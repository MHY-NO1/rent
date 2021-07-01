package com.aliapp.rent.config;

import com.aliapp.rent.entity.po.*;
import com.aliapp.rent.service.*;
import com.aliapp.rent.util.AlipayUtils;
import com.aliapp.rent.util.MessageUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@EnableScheduling   // 1.开启定时任务
@EnableAsync        // 2.开启多线程
public class MultiThreadScheduleTask {

    @Resource
    ProductService productService;

    @Resource
    OrderService orderService;

    @Resource
    TenancyComboService tenancyComboService;

    @Resource
    PreAuthService preAuthService;

    @Resource
    UserInfoService userInfoService;

    @Resource
    ProductExpireService productExpireService;

    @Resource
    FineService fineService;

    @Resource
    VipService vipService;

    @Resource
    CommissionService commissionService;

    @Resource
    CommissionListService commissionListService;

    @Resource
    TransferAccountsService transferAccountsService;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 数据库中把到期商品该为下架状态
     */
    @Async
    @Scheduled(cron = "0 0 0 * * ?") //每天零点
    public void first() {
        //获取过期规则
        ProductExpire productExpire = productExpireService.list().get(0);
        //获取周期
        Integer day = productExpire.getDay();
        //获取销量
        Integer sales = productExpire.getSales();
        //获取现在时间与上次周期时间
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DATE, -day);
        Date old = calendar.getTime();
        //查询所有上架商品
        QueryWrapper<Product> qw = new QueryWrapper<>();
        qw.eq("state", 1);
        List<Product> list = productService.list(qw);
        //收录需要下架的商品合集
        ArrayList<Product> resList = new ArrayList<>();
        //判断list是否有值
        if (list != null && list.size() > 0) {
            //遍历商品
            for (Product p : list) {
                //判断是否在周期内销量不达标
                try {
                    //计算是否达到周期限制
                    int i = daysBetween(p.getCreateDate(), now);
                    //是否到达周期
                    if (i % day == 0) {
                        //达到周期限制！查询周期内销量（订单成交量）
                        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
                        queryWrapper.between("state", 0, 6).between("create_time", old, now).eq(
                                "pid", p.getId());
                        //订单量
                        int count = orderService.count(queryWrapper);
                        //计算订单中的销量
                        // int count = 0;
                        // for (Order order : orderService.list(queryWrapper)) {
                        //     count += order.getNum();
                        // }
                        //判断销量是否小于规定值
                        if (count < sales) {
                            //小于
                            p.setState(2);
                            //加入进集合
                            resList.add(p);
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            productService.updateBatchById(resList);
        }
    }

    /**
     * 日期之间相差的天数
     *
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(betweenDays));
    }

    /**
     * 租赁的商品是否到期，发送到期短信
     */
    @Async
    @Scheduled(cron = "0 0 10 * * ?") //每天十点
    public void second() {
        //查询所有租赁到期订单
        QueryWrapper<Order> qw = new QueryWrapper<>();
        qw.le("exp_time", new Date());
        qw.eq("state", 3);
        List<Order> list = orderService.list(qw);
        //判断list是否有值
        if (list != null && list.size() > 0) {
            //获取罚金
            Fine fine = fineService.list().get(0);
            //遍历订单
            for (Order order : list) {
                //改变订单逾期金额转账状态为有逾期未转账金额
                order.setCompensateTransferState(1);
                //发送短信 每天发送
                Product product = productService.getById(order.getPid());
                MessageUtils.sendMessage(order.getPhone(), product.getName(), "提醒到期归还");
                //扣除租金
                //计算每天的租金价格
                TenancyCombo tenancyCombo = tenancyComboService.getById(order.getTcid());
                Double dayPrice;
                //当前应用的罚金分类
                if (fine.getState() == 0) {
                    //自定义
                    dayPrice = (tenancyCombo.getRent() / tenancyCombo.getTid()) * order.getNum() + fine.getMoney();
                } else {
                    //租金比例
                    dayPrice =
                            (tenancyCombo.getRent() / tenancyCombo.getTid()) * order.getNum() * (1 + fine.getPercent() / 100.0);
                }
                //保留两位数(四舍五入)
                BigDecimal decimal = new BigDecimal(dayPrice);
                Double value = decimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                //获取预授权码
                PreAuth preAuth = preAuthService.getById(order.getPreid());
                //获取买家userid
                UserInfo buyer = userInfoService.getById(order.getUid());
                String outTradeNo = dateFormat.format(new Date()) + (int) Math.random() * 10000;
                String notifyUrl = "https://120.26.0.67:80/notify/compensate";
                //与剩余押金比较
                if ((order.getDepositeAmount() - order.getCompensateAmount()) < value) {
                    //租金大,支付所有剩余押金
                    AlipayUtils.tradePay(outTradeNo, preAuth.getAuthNo(),
                            (order.getDepositeAmount() - order.getCompensateAmount()) + "",
                            "", buyer.getUserId(), notifyUrl,
                            "COMPLETE");
                    //更新支付的违约金额
                    order.setCompensateAmount(order.getDepositeAmount());
                    //押金扣除完，订单状态改为完成
                    order.setState(5);
                    //更新支付金额
                    order.setPaidAmount(order.getPaidAmount() + order.getDepositeAmount() - order.getCompensateAmount());
                } else {
                    //押金大，押金中扣除租金
                    AlipayUtils.tradePay(outTradeNo, preAuth.getAuthNo(),
                            value + "",
                            "", buyer.getUserId(), notifyUrl,
                            "NOT_COMPLETE");
                    //更新支付的违约金额
                    order.setCompensateAmount(order.getCompensateAmount() + value);
                    //更新支付金额
                    order.setPaidAmount(order.getPaidAmount() + value);
                }
                //更新订单
                orderService.updateById(order);
            }
        }
    }

    /**
     * 订单过期，改变订单状态
     */
    @Async
    @Scheduled(cron = "0 0 * * * ? ") //每一小时
    public void third() {
        QueryWrapper<Order> qw = new QueryWrapper<>();
        qw
                .lt("close_time", new Date())
                .eq("state", 0);
        List<Order> list = orderService.list(qw);

        if (list != null && list.size() > 0) {
            ArrayList<Order> orders = new ArrayList<>();
            ArrayList<Product> products = new ArrayList<>();
            for (Order order : list) {
                order.setState(6);
                orders.add(order);
                //更新套餐库存
                TenancyCombo combo = tenancyComboService.getById(order.getTcid());
                UpdateWrapper<TenancyCombo> uw = new UpdateWrapper<>();
                //同商品里同名套餐全部更新库存
                uw.eq("pid", combo.getPid()).eq("cid", combo.getCid()).set("inventory",
                        combo.getInventory() + order.getNum());
                //更新
                tenancyComboService.update(uw);
                //更新商品库存
                Product product = productService.getById(order.getPid());
                product.setInventory(product.getInventory() + order.getNum());
                products.add(product);
            }
            orderService.updateBatchById(orders);
            productService.updateBatchById(products);
        }
    }

    /**
     * 统计各会员等级的会员人数
     */
    @Async
    @Scheduled(cron = "0 30 * * * ?") //每一小时
    public void fourth() {
        //查询所有会员等级
        List<Vip> vipList = vipService.list();
        for (Vip v : vipList) {
            // 获取会员等级下的人数
            int userNum = userInfoService.count(new QueryWrapper<UserInfo>().eq("vid", v.getId()));
            // 对会员人数更新
            v.setUserNum(userNum);
        }
        // 数据库更新
        vipService.updateBatchById(vipList);
    }

    /**
     * 查找过期会员，改变会员等级
     */
    @Async
    @Scheduled(cron = "0 30 0 * * ?") //每天零点半
    public void fifth() {
        //查找level为0的vid
        Vip vip = vipService.getOne(new QueryWrapper<Vip>().eq("level", 0));
        //查找过期会员
        List<UserInfo> userInfoList = userInfoService.list(new QueryWrapper<UserInfo>().ne("vid", vip.getId()).le(
                "vip_expiration_time", new Date()));
        for (UserInfo userInfo : userInfoList) {
            userInfo.setVid(vip.getId());
        }
        //更新数据
        userInfoService.updateBatchById(userInfoList);
    }

    /**
     * 订单到达转账时间，进行转账操作
     */
    @Async
    @Scheduled(cron = "0 30 5 * * ?") //每五点半
    public void sixth() {
        List<Order> list =
                orderService.list(new QueryWrapper<Order>().le("transfer_time", new Date()).
                        eq("transfer_state", 0).ge("state", 3));
        //获取抽成比例
        Commission commission = commissionService.list().get(0);
        Double percentage = 1 - commission.getPercentage() / 100.0;
        ArrayList<CommissionList> commissionLists = new ArrayList<>();
        ArrayList<TransferAccounts> accountsArrayList = new ArrayList<>();
        ArrayList<Order> orders = new ArrayList<>();
        //转账
        for (Order order : list) {
            Product product = productService.getById(order.getPid());
            UserInfo userInfo = userInfoService.getById(product.getPublisherId());
            //转账金额
            String amount = new DecimalFormat("#.00").format(order.getRentAmount() * percentage);
            //转账金额最小为0.1元
            if (Double.valueOf(amount) >= 0.1) {
                String outBizNo = dateFormat.format(new Date()) + (int) Math.random() * 10000;
                String uid = userInfo.getUserId();
                boolean isSuccess = AlipayUtils.transfer(outBizNo, amount, uid);
                //转账成功
                if (isSuccess) {
                    CommissionList commissionList = new CommissionList();
                    TransferAccounts transferAccounts = new TransferAccounts();
                    // 抽成收入表新增数据
                    commissionList.setMoney(order.getRentAmount() - Double.valueOf(amount));
                    commissionList.setOid(order.getId());
                    commissionLists.add(commissionList);
                    // 转账表新增数据
                    transferAccounts.setState(0);
                    transferAccounts.setOid(order.getId());
                    transferAccounts.setOutBizNo(outBizNo);
                    transferAccounts.setTransferAmount(Double.valueOf(amount));
                    transferAccounts.setUid(userInfo.getId());
                    accountsArrayList.add(transferAccounts);
                    //改变订单为已转账
                    order.setTransferState(1);
                    orders.add(order);
                } else {
                    System.out.println("转账失败！OID:" + order.getId());
                }
            } else {
                System.out.println("转账金额小于0.1元，转账失败！");
            }
        }
        if (commissionLists.size() > 0) {
            commissionListService.saveBatch(commissionLists);
        }
        if (accountsArrayList.size() > 0) {
            transferAccountsService.saveBatch(accountsArrayList);
        }
        if (orders.size() > 0) {
            orderService.updateBatchById(orders);
        }
    }

    /**
     * 逾期完成，进行转账
     */
    @Async
    @Scheduled(cron = "0 30 6 * * ?") //每六点半
    public void seventh() {
        List<Order> list = orderService.list(new QueryWrapper<Order>().eq("state", 5).eq("compensate_transfer_state", 1));
        //获取抽成比例
        Commission commission = commissionService.list().get(0);
        Double percentage = 1 - commission.getPercentage() / 100.0;
        ArrayList<CommissionList> commissionLists = new ArrayList<>();
        ArrayList<TransferAccounts> accountsArrayList = new ArrayList<>();
        ArrayList<Order> orders = new ArrayList<>();
        //转账
        for (Order order : list) {
            Product product = productService.getById(order.getPid());
            UserInfo userInfo = userInfoService.getById(product.getPublisherId());
            //转账金额
            String amount = new DecimalFormat("#.00").format(order.getCompensateAmount() * percentage);
            if (Double.valueOf(amount) >= 0.1) {
                String outBizNo = dateFormat.format(new Date()) + (int) Math.random() * 10000;
                String uid = userInfo.getUserId();
                boolean isSuccess = AlipayUtils.transfer(outBizNo, amount, uid);
                //转账成功
                if (isSuccess) {
                    CommissionList commissionList = new CommissionList();
                    TransferAccounts transferAccounts = new TransferAccounts();
                    // 抽成收入表新增数据
                    commissionList.setMoney(order.getCompensateAmount() - Double.valueOf(amount));
                    commissionList.setOid(order.getId());
                    commissionLists.add(commissionList);
                    // 转账表新增数据
                    transferAccounts.setState(3);
                    transferAccounts.setOid(order.getId());
                    transferAccounts.setOutBizNo(outBizNo);
                    transferAccounts.setTransferAmount(Double.valueOf(amount));
                    transferAccounts.setUid(userInfo.getId());
                    accountsArrayList.add(transferAccounts);
                    //改变逾期金额转账状态为正常
                    order.setCompensateTransferState(0);
                    orders.add(order);
                } else {
                    System.out.println("逾期转账失败！OID:" + order.getId());
                }
            } else {
                System.out.println("转账金额小于0.1元，转账失败！");
            }
        }
        if (commissionLists.size() > 0) {
            commissionListService.saveBatch(commissionLists);
        }
        if (accountsArrayList.size() > 0) {
            transferAccountsService.saveBatch(accountsArrayList);
        }
        if (orders.size() > 0) {
            orderService.updateBatchById(orders);
        }
    }
}
