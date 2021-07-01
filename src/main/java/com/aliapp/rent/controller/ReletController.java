package com.aliapp.rent.controller;


import com.aliapp.rent.entity.po.*;
import com.aliapp.rent.entity.vo.ReletVo;
import com.aliapp.rent.service.*;
import com.aliapp.rent.util.AlipayUtils;
import com.aliapp.rent.util.ResponseEntity;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

/**
 * (Relet)表控制层
 *
 * @author makejava
 * @since 2021-04-28 15:59:52
 */
@RestController
@RequestMapping("relet")
public class ReletController {
    /**
     * 服务对象
     */
    @Resource
    private ReletService reletService;

    @Resource
    private OrderService orderService;

    @Resource
    private CommissionService commissionService;

    @Resource
    private CommissionListService commissionListService;

    @Resource
    private TransferAccountsService transferAccountsService;

    @Resource
    private ProductService productService;

    @Resource
    private UserInfoService userInfoService;

    /**
     * 分页查询所有数据
     *
     * @param page  分页对象
     * @param relet 查询实体
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(Page<Relet> page, Relet relet) {
        return ResponseEntity.success(this.reletService.page(page, new QueryWrapper<>(relet)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity selectOne(@PathVariable Serializable id) {
        return ResponseEntity.success(this.reletService.getById(id));
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity insert(@RequestBody ReletVo vo) {
        //判断续租是否成功
        String body = AlipayUtils.tradeQuery(vo.getTradeNo());
        String tradeStatus = JSONObject.parseObject(body).getJSONObject("alipay_trade_query_response").getString(
                "trade_status");

        if ("TRADE_SUCCESS".equals(tradeStatus)) {
            //续租支付成功
            Order order = orderService.getById(vo.getOid());

            //到期时间延长(1、已逾期，2、未逾期)
            Calendar calendar = Calendar.getInstance();
            if (order.getExpTime().before(new Date())) {
                //已逾期
                calendar.setTime(new Date());
            } else {
                //未逾期
                calendar.setTime(order.getExpTime());
            }
            calendar.add(Calendar.DATE, vo.getDay());
            Date time = calendar.getTime();

            //更新到期时间，金额
            order.setExpTime(time);
            order.setPaidAmount(order.getPaidAmount() + vo.getAmount());
            order.setRentAmount(order.getRentAmount() + vo.getAmount());
            order.setTotalAmount(order.getTotalAmount() + vo.getAmount());
            orderService.updateById(order);

            //新增续租记录
            Relet relet = new Relet();
            BeanUtils.copyProperties(vo, relet);
            this.reletService.save(relet);

            //续租完直接转账给商家
            Commission commission = commissionService.list().get(0);
            Double percentage = 1 - commission.getPercentage() / 100.0;
            //转账金额
            String amount = new DecimalFormat("#.00").format(vo.getAmount() * percentage);
            if (Double.valueOf(amount) >= 0.1) {
                Product product = productService.getById(order.getPid());
                UserInfo userInfo = userInfoService.getById(product.getPublisherId());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                String outBizNo = dateFormat.format(new Date()) + (int) Math.random() * 10000;
                String uid = userInfo.getUserId();
                boolean isSuccess = AlipayUtils.transfer(outBizNo, amount, uid);
                //转账成功
                if (isSuccess) {
                    CommissionList commissionList = new CommissionList();
                    TransferAccounts transferAccounts = new TransferAccounts();
                    // 抽成收入表新增数据
                    commissionList.setMoney(vo.getAmount() - Double.valueOf(amount));
                    commissionList.setOid(order.getId());
                    commissionListService.save(commissionList);
                    // 转账表新增数据
                    transferAccounts.setState(1);
                    transferAccounts.setOid(order.getId());
                    transferAccounts.setOutBizNo(outBizNo);
                    transferAccounts.setTransferAmount(Double.valueOf(amount));
                    transferAccounts.setUid(userInfo.getId());
                    transferAccountsService.save(transferAccounts);
                } else {
                    System.out.println("续租转账失败！OID:" + order.getId());
                }
            } else {
                System.out.println("转账金额小于0.1元，转账失败！");
            }
        } else {
            return ResponseEntity.fail("续租失败！");
        }
        return ResponseEntity.success("续租成功！");
    }

    /**
     * 修改数据
     *
     * @param relet 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity update(@RequestBody Relet relet) {
        return ResponseEntity.success(this.reletService.updateById(relet));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity delete(@RequestParam("idList") List<Long> idList) {
        return ResponseEntity.success(this.reletService.removeByIds(idList));
    }
}
