package com.aliapp.rent.controller;


import com.aliapp.rent.entity.po.*;
import com.aliapp.rent.entity.vo.ServiceOrderVo;
import com.aliapp.rent.service.*;
import com.aliapp.rent.util.AlipayUtils;
import com.aliapp.rent.util.KdniaoSubscribePush;
import com.aliapp.rent.util.MessageUtils;
import com.aliapp.rent.util.ResponseEntity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * (ServiceOrder)表控制层
 *
 * @author makejava
 * @since 2021-04-26 11:23:41
 */
@RestController
@RequestMapping("serviceOrder")
public class ServiceOrderController {
    /**
     * 服务对象
     */
    @Resource
    private ServiceOrderService serviceOrderService;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private OrderService orderService;

    @Resource
    private ProductService productService;

    @Resource
    private PreAuthService preAuthService;

    @Resource
    private LogisticsService logisticsService;

    @Resource
    private TenancyComboService tenancyComboService;

    @Resource
    private ExpressService expressService;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 分页查询所有数据
     *
     * @param page         分页对象
     * @param serviceOrder 查询实体
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(Page<ServiceOrder> page,
                                    ServiceOrder serviceOrder) {
        return ResponseEntity.success(this.serviceOrderService.page(page,
                new QueryWrapper<>(serviceOrder)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity selectOne(@PathVariable Serializable id) {
        return ResponseEntity.success(this.serviceOrderService.getById(id));
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity insert(@RequestBody ServiceOrderVo vo) {
        Order order = orderService.getById(vo.getOid());
        //判断是否冻结成功！
        if (order.getPreid() != null) {
            UserInfo buyer = userInfoService.getById(vo.getUid());
            //创建服务订单
            ServiceOrder serviceOrder = new ServiceOrder();
            String outBizNo = dateFormat2.format(new Date()) + (int) Math.random() * 10000;
            vo.setOutBizNo(outBizNo);
            vo.setBuyerId(buyer.getUserId());
            BeanUtils.copyProperties(vo, serviceOrder);
            this.serviceOrderService.save(serviceOrder);
            Product product = productService.getById(order.getPid());
            //支付租金
            UserInfo seller = userInfoService.getById(product.getPublisherId());
            PreAuth preAuth = preAuthService.getById(order.getPreid());
            String notifyUrl = "https://120.26.0.67:80/notify/paid";
            String outTradeNo = dateFormat2.format(new Date()) + (int) Math.random() * 10000;

            //更新order状态
            order.setSid(serviceOrder.getId());
            //更新该值使异步通知可以锁定oid
            order.setOutTradeNo(outTradeNo);
            order.setPaidAmount(order.getRentAmount());
            order.setOutTradeNo(outTradeNo);
            orderService.updateById(order);

            //即时异步通知
            AlipayUtils.tradePay(outTradeNo, preAuth.getAuthNo(),
                    order.getRentAmount() + "",
                    seller.getUserId(), buyer.getUserId(), notifyUrl,
                    "NOT_COMPLETE");

            return ResponseEntity.success();
        }

        return ResponseEntity.fail("服务订单创建失败！");
    }

    /**
     * 修改数据
     * 更新物流状态
     *
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity update(@RequestBody ServiceOrderVo vo) {
        Order order = orderService.getById(vo.getOid());

        //获取快递公司信息
        QueryWrapper<Express> qw = new QueryWrapper<>();
        qw.eq("name", vo.getLogisticsCode());
        Express express = expressService.getOne(qw);

        ServiceOrder serviceOrder = serviceOrderService.getById(order.getSid());

        //订阅物流信息
        KdniaoSubscribePush push = new KdniaoSubscribePush();
        String bizId = push.orderOnlineByJson(serviceOrder.getId() + "-" + order.getId() +
                        "-" + vo.getType(),
                express.getCode(),
                vo.getTrackingNo(), order.getPhone());

        if (!StringUtils.hasText(bizId)) {
            return ResponseEntity.fail("订阅失败，单号有误！");
        }

        String phone;
        Product product = productService.getById(order.getPid());

        //新增物流信息
        Logistics logistics = new Logistics();
        logistics.setBusinessId(bizId);
        logistics.setEid(express.getId());
        logistics.setTrackingNo(vo.getTrackingNo());
        logisticsService.save(logistics);

        //判断是归还还是发货
        if ("back".equals(vo.getType())) {
            order.setState(4);
            //更新物流信息 归还时
            serviceOrder.setReturnId(logistics.getId());
            phone = product.getPhone();
        } else {
            order.setState(2);
            //更新物流信息 发货时
            serviceOrder.setDeliverId(logistics.getId());
            phone = order.getPhone();
        }
        //更新订单状态
        orderService.updateById(order);
        //发送发货短信
        MessageUtils.sendMessage(phone, product.getName(), "发货");

        return ResponseEntity.success(this.serviceOrderService.updateById(serviceOrder));
    }

    /**
     * 接受物流信息通知
     */
    @PostMapping("logisticsInfo")
    public String logisticsInfo(String RequestData) {
        //获取字符串，转JSON
        JSONObject jsonObject = JSON.parseObject(RequestData);
        JSONObject data = jsonObject.getJSONArray("Data").getJSONObject(0);
        //获取CallBack
        String callBack = data.getString("CallBack");
        String[] split = callBack.split("-");
        if (split.length != 3) {
            return "无CallBack传递！";
        }
        //0:sid 1:oid 2:type（发货deliver还是归还back）
        ServiceOrder serviceOrder = serviceOrderService.getById(split[0]);
        Logistics logistics;
        //判断是发货还是归还
        boolean flag = "back".equals(split[2]);
        if (flag) {
            logistics = logisticsService.getById(serviceOrder.getReturnId());
        } else {
            logistics = logisticsService.getById(serviceOrder.getDeliverId());
        }

        //获取是否成功
        Boolean success = data.getBoolean("Success");
        String json = "{\n" +
                "    \"EBusinessID\":\"" + logistics.getBusinessId() + "\",\n" +
                "    \"UpdateTime\":\"" + dateFormat.format(new Date()) + "\",\n" +
                "    \"Success\":true,\n" +
                "    \"Reason\":\"\"\n" +
                "}";

        //构造返回json
        // JSONObject response = JSON.parseObject(json);
        // *
        //  * 1、若Data接收到推送Success为false，且Reason为三天无轨迹或七天内无轨迹变化，需重新发起订阅；
        //  * 2、若Data接收到推送Success为false，且State为0，属于正常推送，需耐心等待下次推送即可；

        if (!success) {
            return json;
        }

        /*//获取轨迹列表
        JSONArray traces = data.getJSONArray("Traces");
        logistics.setTraces(traces.toJSONString());
        //通过最新轨迹获取最新物流信息
        JSONObject info = traces.getJSONObject(traces.size() - 1);
        String action = info.getString("Action");*/


        //物流信息状态
        // * 增值物流状态： 1-已揽收， 2-在途中， 201-到达派件城市， 202-派件中， 211-已放入快递柜或驿站， 3-已签收，
        // * 311-已取出快递柜或驿站， 4-问题件， 401-发货无信息， 402-超时未签收， 403-超时未更新， 404-拒收（退件），
        // * 412-快递柜或驿站超时未取
        // *

        //获取之前物流信息的状态
        String state = logistics.getState();
        //判断之前的物流信息是否已经完成，完成则不用再次发送短信
        boolean isSuccess = false;
        if (StringUtils.hasText(state) && "3".equals(state.split("")[0])) {
            isSuccess = true;
        }
        //获取物流最新状态StateEx（增值）
        String stateEx = data.getString("StateEx");
        //更新物流信息状态
        logistics.setState(stateEx);
        logisticsService.updateById(logistics);
        //取当前物流信息的第一位数字 302->3
        Integer type = Integer.valueOf(stateEx.split("")[0]);

        //获取当前订单信息
        Order order = orderService.getById(split[1]);
        //获取商品信息（用于短信发送）
        Product product = productService.getById(order.getPid());
        //获取电话(短信发送)
        String phone;
        //根据物流信息改变订单状态
        if (type == 3 && !isSuccess) {
            if (!flag) {
                //发货
                //订单未更新为待归还
                if (order.getState() != 3) {
                    //更新租品到期时间
                    Calendar instance = Calendar.getInstance();
                    instance.setTime(new Date());
                    TenancyCombo combo = tenancyComboService.getById(order.getTcid());
                    instance.add(Calendar.DATE, combo.getTid());
                    order.setExpTime(instance.getTime());
                    order.setState(3);
                    //设置转账时间  3天后
                    instance.setTime(new Date());
                    instance.add(Calendar.DATE, 3);
                    order.setTransferTime(instance.getTime());
                    orderService.updateById(order);
                }
                //发送短信至商家
                phone = product.getPhone();
            } else {
                //归还
                //发送短信至买家
                phone = order.getPhone();
            }
            //发送收货短信
            MessageUtils.sendMessage(phone, product.getName(), "收货");
            // logistics.setState("已签收");
        }

        /*switch (type) {
            case 1:
                logistics.setState("已揽收");
                break;
            case 2:
                logistics.setState("在途中");
                break;
            case 201:
                logistics.setState("到达派件城市");
                break;
            case 202:
                logistics.setState("派件中");
                break;
            case 211:
                logistics.setState("已放入快递柜或驿站");
                break;
            case 3:
                if (!flag) {
                    //发货
                    if (order.getState() != 3) {
                        //更新订单到期时间
                        Calendar instance = Calendar.getInstance();
                        instance.setTime(new Date());
                        TenancyCombo combo = tenancyComboService.getById(order.getTcid());
                        instance.add(Calendar.DATE, combo.getTid());
                        order.setExpTime(instance.getTime());
                        order.setState(3);
                        orderService.updateById(order);
                    }
                    //发送短信至商家
                    phone = product.getPhone();
                } else {
                    //归还
                    //发送短信至买家
                    phone = order.getPhone();
                }
                //发送收货短信
                MessageUtils.sendMessage(phone, product.getName(), "收货");
                logistics.setState("已签收");
                break;
            case 311:
                if (!flag) {
                    //发货
                    if (order.getState() != 3) {
                        //更新订单到期时间
                        Calendar instance = Calendar.getInstance();
                        instance.setTime(new Date());
                        TenancyCombo combo = tenancyComboService.getById(order.getTcid());
                        instance.add(Calendar.DATE, combo.getTid());
                        order.setExpTime(instance.getTime());
                        order.setState(3);
                        orderService.updateById(order);
                    }
                }
                logistics.setState("已取出快递柜或驿站");
                break;
            case 4:
                logistics.setState("问题件");
                break;
            case 401:
                logistics.setState("发货无信息");
                break;
            case 402:
                logistics.setState("超时未签收");
                break;
            case 403:
                logistics.setState("超时未更新");
                break;
            case 404:
                logistics.setState("拒收（退件）");
                break;
            case 412:
                logistics.setState("快递柜或驿站超时未取");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }*/
        // logisticsService.updateById(logistics);

        return json;
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity delete(@RequestParam("idList") List<Long> idList) {
        return ResponseEntity.success(this.serviceOrderService.removeByIds(idList));
    }
}
