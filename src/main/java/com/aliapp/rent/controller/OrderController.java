package com.aliapp.rent.controller;


import com.aliapp.rent.entity.po.*;
import com.aliapp.rent.entity.vo.OrderProTenComVo;
import com.aliapp.rent.entity.vo.OrderVo;
import com.aliapp.rent.service.*;
import com.aliapp.rent.util.AlipayUtils;
import com.aliapp.rent.util.ResponseEntity;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * (Order)表控制层
 *
 * @author makejava
 * @since 2021-04-13 16:57:49
 */
@RestController
@RequestMapping("order")
public class OrderController {
    /**
     * 服务对象
     */
    @Resource
    private OrderService orderService;

    @Resource
    private ProductService productService;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private TenancyComboService tenancyComboService;

    @Resource
    private PreAuthService preAuthService;

    @Resource
    private ServiceOrderService serviceOrderService;

    @Resource
    private CommissionService commissionService;

    @Resource
    private CommissionListService commissionListService;

    @Resource
    private TransferAccountsService transferAccountsService;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(Page<OrderVo> page, OrderVo vo) {
        Order order = new Order();
        BeanUtils.copyProperties(vo, order);
        QueryWrapper<Order> qw = new QueryWrapper<>();
        qw.eq(vo.getState() != null, "o.state", vo.getState());
        qw.eq("o.uid", vo.getUid());
        qw.orderByDesc("o.create_time");
        return ResponseEntity.success(this.orderService.queryOrder(page, qw));
    }

    @GetMapping("list")
    public ResponseEntity list(OrderVo vo) {
        Order order = new Order();
        BeanUtils.copyProperties(vo, order);
        return ResponseEntity.success(this.orderService.list(new QueryWrapper<>(order)));
    }

    /**
     * 订单统计（总订单数）
     */
    @GetMapping("count/{type}")
    public List count(@PathVariable String type) {
        QueryWrapper<Order> qw = new QueryWrapper<Order>().between("r.state", 1, 6);
        List<OrderVo> orderVoList = null;

        switch (type) {
            case "all":
                orderVoList = orderService.queryOrderCount(qw);
                break;
            case "category":
                orderVoList = orderService.queryOrderByCategoryId(qw.groupBy("r.cid"));
                break;
            case "product":
                orderVoList = orderService.queryProductOrderNum(qw.groupBy("r.pid"));
                break;
            default:
        }
        return orderVoList;
    }

    /**
     * 设置订单过期倒计时
     */
    @PutMapping("countdown")
    public ResponseEntity countdown(@RequestBody OrderVo vo) {
        Order order = orderService.getById(vo.getId());
        //判断是否已经有倒计时
        if (order.getCloseTime() != null) {
            //判断倒计时是否计时完成
            if (new Date().after(order.getCloseTime())) {
                //取消状态
                order.setState(6);
                orderService.updateById(order);
                //恢复库存
                //更新套餐库存
                TenancyCombo combo = tenancyComboService.getById(order.getTcid());
                UpdateWrapper<TenancyCombo> uw = new UpdateWrapper<>();
                //商品类同名套餐全部更新库存
                uw.eq("pid", combo.getPid()).eq("cid", combo.getCid()).set("inventory",
                        combo.getInventory() + order.getNum());
                //更新
                tenancyComboService.update(uw);
                //更新商品库存
                Product product = productService.getById(order.getPid());
                product.setInventory(product.getInventory() + order.getNum());
                productService.updateById(product);

                return ResponseEntity.fail("订单已取消！");
            }
            long l = (order.getCloseTime().getTime() - System.currentTimeMillis()) / 1000;
            return ResponseEntity.success(l);
        }
        //设置30分钟倒计时
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 30);
        order.setCloseTime(calendar.getTime());
        orderService.updateById(order);
        return ResponseEntity.success(30 * 60);
    }


    /**
     * 查询我的出租订单
     */
    @GetMapping("myRent")
    public ResponseEntity myRent(Page<OrderVo> page, OrderVo vo) {
        Order order = new Order();
        BeanUtils.copyProperties(vo, order);
        QueryWrapper<Order> qw = new QueryWrapper<>();
        qw.eq(vo.getState() != null, "o.state", vo.getState());
        qw.eq("tc.publisher_id", vo.getPublisherId());
        qw.orderByDesc("o.create_time");
        return ResponseEntity.success(this.orderService.queryMyRent(page, qw));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity selectOne(@PathVariable Serializable id) {
        OrderProTenComVo orderProTenComVo = new OrderProTenComVo();
        //获取订单信息
        Order byId = this.orderService.getById(id);
        //获取商品信息
        Product byId1 = this.productService.getById(byId.getPid());
        // 获取套餐信息
        TenancyCombo byId2 = this.tenancyComboService.getById(byId.getTcid());
        if (byId.getPreid() != null) {
            //获取预授权信息
            PreAuth byId3 = this.preAuthService.getById(byId.getPreid());
            orderProTenComVo.setPreAuth(byId3);
        }
        if (byId.getSid() != null) {
            //获取服务信息
            ServiceOrder byId3 = serviceOrderService.getById(byId.getSid());
            orderProTenComVo.setServiceOrder(byId3);
        }
        orderProTenComVo.setOrder(byId);
        orderProTenComVo.setProduct(byId1);
        orderProTenComVo.setTenancyCombo(byId2);
        return ResponseEntity.success(orderProTenComVo);
    }

    /**
     * 确认收货
     *
     * @param id
     * @return
     */
    @GetMapping("receive/{id}")
    public ResponseEntity receive(@PathVariable Serializable id) {
        Order order = orderService.getById(id);
        //更新订单状态
        order.setState(3);
        //更新订单到期时间
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        TenancyCombo combo = tenancyComboService.getById(order.getTcid());
        instance.add(Calendar.DATE, combo.getTid());
        order.setExpTime(instance.getTime());

        orderService.updateById(order);

        //更新商品库存（保留）

        return ResponseEntity.success();
    }

    /**
     * 确认完成交易
     *
     * @param id
     * @return
     */
    @GetMapping("confirm/{id}")
    public ResponseEntity confirm(@PathVariable Serializable id) {
        Order order = orderService.getById(id);
        //更新订单状态
        order.setState(5);

        orderService.updateById(order);

        //不考虑损坏赔偿情况，解冻剩余押金
        PreAuth preAuth = preAuthService.getById(order.getPreid());

        String notifyUrl = "https://120.26.0.67:80/notify/confirmNotify";
        AlipayUtils.fundAuthOrderUnFreeze(preAuth.getAuthNo(),
                order.getDepositeAmount() + "",
                notifyUrl);


        return ResponseEntity.success();
    }

    /**
     * 获取退款理由
     *
     * @param id
     * @return
     */
    @GetMapping("getOne/{id}")
    public ResponseEntity getOne(@PathVariable Serializable id) {
        Order order = orderService.getById(id);
        String refundReason = order.getRefundReason();
        return ResponseEntity.success(refundReason);
    }

    /**
     * 申请退款
     */
    @PostMapping("refund")
    public ResponseEntity refund(@RequestBody OrderVo vo) {
        Order order = orderService.getById(vo.getId());
        order.setApplyRefund(1);
        order.setRefundReason(vo.getRefundReason());
        orderService.updateById(order);
        return ResponseEntity.success();
    }

    /**
     * 获取续租金额
     */
    @PostMapping("relet")
    public ResponseEntity relet(@RequestBody OrderVo vo) {
        Order order = orderService.getById(vo.getId());

        //获取数量
        Integer num = order.getNum();

        TenancyCombo combo = tenancyComboService.getById(order.getTcid());
        String cid = combo.getCid();
        Integer pid = combo.getPid();

        //根据pid , cid,tid获取新租金
        TenancyCombo tenancyCombo = new TenancyCombo();
        tenancyCombo.setCid(cid);
        tenancyCombo.setPid(pid);
        tenancyCombo.setTid(vo.getTid());
        TenancyCombo one = tenancyComboService.getOne(new QueryWrapper<>(tenancyCombo));

        Double amount = one.getRent() * num;

        return ResponseEntity.success(amount);
    }

    /**
     * 续租tradeNo获取
     */
    @PostMapping("reletPay")
    public ResponseEntity reletPay(@RequestBody OrderVo vo) {
        Order order = orderService.getById(vo.getId());

        Product product = productService.getById(order.getPid());

        UserInfo userInfo = userInfoService.getById(order.getUid());


        Double reletAmount = vo.getReletAmount();


        String tradeNo = AlipayUtils.getTradeNo(UUID.randomUUID().toString().replace(
                "-", ""),
                reletAmount + "", product.getName() + "续租",
                userInfo.getUserId());

        if (tradeNo == null) {
            return ResponseEntity.fail("获取tradeNo失败！");
        }

        return ResponseEntity.success(tradeNo);
    }

    /**
     * 买断
     */
    @PostMapping("buyOut")
    public ResponseEntity buyOut(@RequestBody OrderVo vo) {
        Order order = orderService.getById(vo.getId());
        Product product = productService.getById(order.getPid());
        UserInfo userInfo = userInfoService.getById(vo.getUid());
        TenancyCombo combo = tenancyComboService.getById(order.getTcid());

        String amount;
        //买断总金额
        Double totalBuyOutAmount = combo.getBuyOutAmount() * order.getNum();
        //冻结总金额
        Double freezeAmount = order.getTotalAmount();
        //比较买断总金额与冻结总金额的大小
        if (totalBuyOutAmount > freezeAmount) {
            //买断金额大，需要额外支付
            amount = totalBuyOutAmount - freezeAmount + "";
        } else {
            //冻结金额大或等，扣除押金，归还剩余金额
            amount = "0.01";
        }

        String tradeNo = AlipayUtils.getTradeNo(UUID.randomUUID().toString().replace("-", ""),
                amount, product.getName(), userInfo.getUserId());

        if (tradeNo == null) {
            return ResponseEntity.fail("获取TradeNo失败！");
        }
        return ResponseEntity.success(tradeNo);
    }

    /**
     * 买断成功
     */
    @PostMapping("buyOut/success")
    public ResponseEntity buyOutSuc(@RequestBody OrderVo vo) {
        Order order = orderService.getById(vo.getId());
        TenancyCombo combo = tenancyComboService.getById(order.getTcid());
        PreAuth preAuth = preAuthService.getById(order.getPreid());
        Product product = productService.getById(order.getPid());
        UserInfo buyer = userInfoService.getById(order.getUid());
        UserInfo seller = userInfoService.getById(product.getPublisherId());
//        ServiceOrder serviceOrder = serviceOrderService.getById(order.getSid());

        //判断支付是否成功
        String body = AlipayUtils.tradeQuery(vo.getTradeNo());
        String tradeStatus = JSONObject.parseObject(body).getJSONObject(
                "alipay_trade_query_response").getString("trade_status");
        //成功
        if ("TRADE_SUCCESS".equalsIgnoreCase(tradeStatus)) {
            String amount;
            //买断总金额
            Double totalBuyOutAmount = combo.getBuyOutAmount() * order.getNum();
            //冻结总金额
            Double freezeAmount = order.getTotalAmount();
            //逾期金额
            Double compensateAmount = 0.0;
            if (order.getCompensateAmount() != null && order.getCompensateAmount() > 0) {
                //改逾期未转账为正常
                order.setCompensateTransferState(0);
                compensateAmount = order.getCompensateAmount();
            }

            //比较买断总金额与冻结总金额的大小
            if (totalBuyOutAmount > freezeAmount) {
                //买断金额大，支付所有押金，用总额减去已支付金额
                amount = order.getTotalAmount() - order.getPaidAmount() + "";
                // amount = order.getDepositeAmount() + "";
            } else {
                //冻结金额大或等，扣除押金，归还剩余金额，算出逾期金额
                amount =
                        // (order.getDepositeAmount() - (freezeAmount - totalBuyOutAmount)) - (order.getPaidAmount() - order
                        // .getRentAmount()) - 0.01 + "";
                        (order.getDepositeAmount() - (freezeAmount - totalBuyOutAmount)) - compensateAmount - 0.01 + "";

            }
            String outTradeNo = dateFormat.format(new Date()) + (int) Math.random() * 10000;
            String notifyUrl = "https://120.26.0.67:80/notify/buyOut";
            //即时异步通知
            AlipayUtils.tradePay(outTradeNo, preAuth.getAuthNo(), amount,
                    seller.getUserId(), buyer.getUserId(), notifyUrl, "COMPLETE");

            //进行商家转账操作
            //判断是否有逾期
            //转账金额(无抽成)
            String transferAmount = new DecimalFormat("#.00").format((totalBuyOutAmount - order.getRentAmount()));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String outBizNo = dateFormat.format(new Date()) + (int) Math.random() * 10000;
            boolean isSuccess = AlipayUtils.transfer(outBizNo, transferAmount, seller.getUserId());
            //转账成功
            if (isSuccess) {
                TransferAccounts transferAccounts = new TransferAccounts();
                // 转账表新增数据
                transferAccounts.setState(2);
                transferAccounts.setOid(order.getId());
                transferAccounts.setOutBizNo(outBizNo);
                transferAccounts.setTransferAmount(Double.valueOf(transferAmount));
                transferAccounts.setUid(seller.getId());
                transferAccountsService.save(transferAccounts);
            } else {
                System.out.println("买断转账失败！OID:" + order.getId());
            }

            //更新订单为完成状态
            order.setState(5);
            order.setPaidAmount(totalBuyOutAmount);
            orderService.updateById(order);

            return ResponseEntity.success("买断成功！");
        } else {
            return ResponseEntity.fail("买断失败！");
        }

        /*//交易结果查询
        String json = AlipayUtils.preAuthTradeQuery(outTradeNo);
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONObject response = jsonObject.getJSONObject("alipay_trade_query_response");

        String tradeStatus = response.getString("trade_status");

        if (tradeStatus.equalsIgnoreCase("TRADE_SUCCESS")) {
            return ResponseEntity.success("买断成功！");
        }

        //冻结资金支付失败，开始退款操作！
        String refundAmount =
                combo.getBuyOutAmount() * order.getNum() - order.getTotalAmount() + "";

        AlipayUtils.tradeRefund(vo.getTradeNo(), refundAmount);*/

        // return ResponseEntity.fail("买断失败！");
    }

    //商家操作退款
    @PutMapping("refund")
    public ResponseEntity updateRefund(@RequestBody OrderVo vo) {
        Order order = orderService.getById(vo.getId());
        //确认退款
        if (vo.getApplyRefund() == 2) {
            //退款
            AlipayUtils.refund(order.getOutTradeNo(), order.getRentAmount() + "");
            order.setApplyRefund(2);
            order.setState(6);
            orderService.updateById(order);
            //解冻押金
            PreAuth preAuth = preAuthService.getById(order.getPreid());
            String notifyUrl = "https://120.26.0.67:80/notify/unfreeze";
            AlipayUtils.fundAuthOrderUnFreeze(preAuth.getAuthNo(),
                    order.getDepositeAmount() + "",
                    notifyUrl);
        }
        //拒绝退款
        else if (vo.getApplyRefund() == 3) {
            order.setApplyRefund(3);
            orderService.updateById(order);
        }
        return ResponseEntity.success();
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity insert(@RequestBody OrderVo vo) {
        Product product;
        Double amount;

        String orederNo = dateFormat.format(new Date()) + (int) Math.random() * 10000;
        String reqNo = orederNo + 1;

        //第一次点击购买
        if (vo.getId() == null) {
            QueryWrapper<TenancyCombo> qw = new QueryWrapper<>();
            qw.eq("tid", vo.getTid());
            qw.eq("cid", vo.getCid());
            qw.eq("pid", vo.getPid());
            TenancyCombo tenancyCombo = tenancyComboService.getOne(qw);
            product = productService.getById(vo.getPid());
//            UserInfo userInfo = userInfoService.getById(vo.getUid());

            if (tenancyCombo.getInventory() < vo.getNum()) {
                return ResponseEntity.fail("库存不足！");
            }
            vo.setTcid(tenancyCombo.getId());
            vo.setDepositeAmount(tenancyCombo.getDeposite() * vo.getNum());
            vo.setRentAmount(tenancyCombo.getRent() * vo.getNum());
//            amount = (tenancyCombo.getRent() + tenancyCombo.getDeposite()) * vo
//            .getNum();
            amount = vo.getDepositeAmount() + vo.getRentAmount();
            vo.setTotalAmount(amount);
            vo.setOutTradeNo(orederNo);
            //更新商品总库存
            product.setInventory(product.getInventory() - vo.getNum());
            productService.updateById(product);
            //更新套餐库存
            UpdateWrapper<TenancyCombo> uw = new UpdateWrapper<>();
            //商品类同名套餐全部更新库存
            uw.eq("pid", tenancyCombo.getPid()).eq("cid", tenancyCombo.getCid()).set("inventory",
                    tenancyCombo.getInventory() - vo.getNum());
            //更新
            tenancyComboService.update(uw);
        } else {
            //订单详情的冻结押金
            Order byId = orderService.getById(vo.getId());
            product = productService.getById(byId.getPid());
            amount = byId.getTotalAmount();
        }

        //获得orderStr
        String orderTitle = product.getName() + "预授权";
        String notifyUrl = "https://120.26.0.67:80/notify/freeze";
        String orderStr = AlipayUtils.fundAuthOrderAppFreeze(orderTitle, amount + ""
                , notifyUrl, "RENT_PHONE", orederNo, reqNo);

        vo.setOrderStr(orderStr);

        if (vo.getId() == null) {
            Order order = new Order();
            BeanUtils.copyProperties(vo, order);
            orderService.saveOrUpdate(order);
            vo.setId(order.getId());
        }
        return ResponseEntity.success(vo);
    }

    /**
     * 更新评论
     */
    @PutMapping("remark")
    public ResponseEntity remark(@RequestBody OrderVo vo) {
        Order order = new Order();
        BeanUtils.copyProperties(vo, order);
        return ResponseEntity.success(this.orderService.updateById(order));
    }

    /**
     * 修改数据
     *
     * @return 修改结果 取消订单
     */
    @PutMapping
    public ResponseEntity update(@RequestBody OrderVo vo) {
        //获取订单数据
        Order order = orderService.getById(vo.getId());
        //更改订单为取消状态
        order.setState(6);
        this.orderService.updateById(order);

        //恢复库存
        //更新套餐库存
        TenancyCombo combo = tenancyComboService.getById(order.getTcid());
        UpdateWrapper<TenancyCombo> uw = new UpdateWrapper<>();
        //商品类同名套餐全部更新库存
        uw.eq("pid", combo.getPid()).eq("cid", combo.getCid()).set("inventory",
                combo.getInventory() + order.getNum());
        //更新
        tenancyComboService.update(uw);
        //更新商品库存
        Product product = productService.getById(order.getPid());
        product.setInventory(product.getInventory() + order.getNum());
        productService.updateById(product);

        return ResponseEntity.success();
    }

    @PostMapping("edit")
    public ResponseEntity edit(OrderVo vo) {
        Order order = new Order();
        BeanUtils.copyProperties(vo, order);
        return ResponseEntity.success(this.orderService.updateById(order));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity delete(@RequestParam("idList") List<Long> idList) {
        return ResponseEntity.success(this.orderService.removeByIds(idList));
    }
}
