package com.aliapp.rent.controller;

import com.aliapp.rent.entity.po.Order;
import com.aliapp.rent.entity.po.PreAuth;
import com.aliapp.rent.entity.po.Product;
import com.aliapp.rent.entity.po.TenancyCombo;
import com.aliapp.rent.service.OrderService;
import com.aliapp.rent.service.PreAuthService;
import com.aliapp.rent.service.ProductService;
import com.aliapp.rent.service.TenancyComboService;
import com.aliapp.rent.util.AlipayUtils;
import com.aliapp.rent.util.MessageUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 异步通知
 * @author sda
 */
@RestController
@RequestMapping("notify")
public class NotifyController {

    @Resource
    private PreAuthService preAuthService;

    @Resource
    private OrderService orderService;

    @Resource
    private TenancyComboService tenancyComboService;

    @Resource
    private ProductService productService;

    //解冻异步通知
    @PostMapping("unfreeze")
    public String unfreeze(HttpServletRequest request) {
        Map map = AlipayUtils.asyncCertificate(request);
        String status = (String) map.get("status");
        String authNo = (String) map.get("auth_no");

        //返回库存更新
        if ("SUCCESS".equalsIgnoreCase(status)) {
            QueryWrapper<PreAuth> preqw = new QueryWrapper<>();
            preqw.eq("auth_no", authNo);
            PreAuth preAuth = preAuthService.getOne(preqw);
            if (preAuth != null) {
                QueryWrapper<Order> qw = new QueryWrapper<>();
                qw.eq("preid", preAuth.getId());
                Order order = orderService.getOne(qw);
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
            }
        } else {
            System.out.println("解冻失败！");
        }
        return "success";
    }

    /**
     * 冻结异步通知
     */
    @PostMapping("freeze")
    public String freeze(HttpServletRequest request) {
        Map map = AlipayUtils.asyncCertificate(request);
        String outOrderNo = (String) map.get("out_order_no");
        String status = (String) map.get("status");
        String payerUserId = (String) map.get("payer_user_id");
        String authNo = (String) map.get("auth_no");
        Double creditAmount = map.get("credit_amount") != null ? (Double) map.get(
                "credit_amount") : 0;
        Double fundAmount = map.get("fund_amount") != null ? (Double) map.get(
                "fund_amount") : 0;
        Double amount = map.get("amount") != null ?
                Double.parseDouble((String) map.get("amount")) : 0;

        //冻结成功
        if ("SUCCESS".equalsIgnoreCase(status)) {

            PreAuth preAuth = new PreAuth();
            preAuth.setOrderNo(outOrderNo);
            //判断preid是否存在
            if (preAuthService.getOne(new QueryWrapper<>(preAuth)) == null) {
                //新增预授权信息
                preAuth.setStatus(1);
                preAuth.setPayerUserId(payerUserId);
                preAuth.setAuthNo(authNo);
                preAuth.setAmount(amount);
                preAuth.setCreditAmount(creditAmount);
                preAuth.setFundAmount(fundAmount);
                preAuthService.save(preAuth);

                //添加preid到order里
                Map<String, Object> hashMap = new HashMap<>();
                hashMap.put("out_trade_no", outOrderNo);
                List<Order> orders = orderService.listByMap(hashMap);
                if (orders.size() > 0) {
                    Order order = orders.get(0);
                    order.setPreid(preAuth.getId());
                    orderService.updateById(order);
                }
            }
        }
        return "success";
    }

    //授权转支付成功异步通知
    @PostMapping("paid")
    public String paid(HttpServletRequest request) {
        Map map = AlipayUtils.asyncCertificate(request);
        String tradeNo = (String) map.get("trade_no");
        String outTradeNo = (String) map.get("out_trade_no");
        String tradeStatus = (String) map.get("trade_status");

        if ("TRADE_SUCCESS".equalsIgnoreCase(tradeStatus)) {
            //支付成功
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("out_trade_no", outTradeNo);
            List<Order> orders = orderService.listByMap(hashMap);
            if (orders.size() > 0) {
                Order order = orders.get(0);
                order.setTradeNo(tradeNo);
                order.setState(1);
                orderService.updateById(order);
                Product product = productService.getById(order.getPid());
                /*//更新库存数量
                TenancyCombo combo = tenancyComboService.getById(order.getTcid());
                combo.setInventory(combo.getInventory() - order.getNum());
                tenancyComboService.updateById(combo);
                //更新商品库存
                Product product = productService.getById(order.getPid());
                product.setInventory(product.getInventory() - order.getNum());
                productService.updateById(product);*/
                // 提醒发货短信
                MessageUtils.sendMessage(product.getPhone(), product.getName(), "提醒发货");
            }
        }
        return "success";
    }

    //买断成功异步通知
    @PostMapping("buyOut")
    public String buyOutNotify(HttpServletRequest request) {
        Map map = AlipayUtils.asyncCertificate(request);
        String tradeStatus = (String) map.get("trade_status");

        if ("TRADE_SUCCESS".equalsIgnoreCase(tradeStatus)) {
            //买断成功！
            System.out.println("买断成功！");
        }
        return "success";
    }

    //确认完成交易异步通知
    @PostMapping("confirmNotify")
    public String confirmNotify(HttpServletRequest request) {
        Map map = AlipayUtils.asyncCertificate(request);
        return "success";
    }

    //逾期金额支付异步通知
    @PostMapping("compensate")
    public String compensate(HttpServletRequest request) {
        Map map = AlipayUtils.asyncCertificate(request);
        String tradeNo = (String) map.get("trade_no");
        String outTradeNo = (String) map.get("out_trade_no");
        String tradeStatus = (String) map.get("trade_status");

        if ("TRADE_SUCCESS".equalsIgnoreCase(tradeStatus)) {
            //支付成功
        }
        return "success";
    }
}
