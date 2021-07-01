package com.aliapp.rent.controller;


import com.aliapp.rent.entity.po.Express;
import com.aliapp.rent.entity.po.Logistics;
import com.aliapp.rent.entity.po.Order;
import com.aliapp.rent.entity.po.ServiceOrder;
import com.aliapp.rent.entity.vo.LogisticsVo;
import com.aliapp.rent.service.ExpressService;
import com.aliapp.rent.service.LogisticsService;
import com.aliapp.rent.service.OrderService;
import com.aliapp.rent.service.ServiceOrderService;
import com.aliapp.rent.util.KdniaoSubscribe;
import com.aliapp.rent.util.ResponseEntity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Logistics)表控制层
 *
 * @author makejava
 * @since 2021-05-07 10:59:37
 */
@RestController
@RequestMapping("logistics")
public class LogisticsController {
    /**
     * 服务对象
     */
    @Resource
    private LogisticsService logisticsService;

    @Resource
    private OrderService orderService;

    @Resource
    private ServiceOrderService serviceOrderService;

    @Resource
    private ExpressService expressService;

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(LogisticsVo vo) {
        Logistics logistics = new Logistics();
        BeanUtils.copyProperties(vo, logistics);
        return ResponseEntity.success(logisticsService.list(new QueryWrapper<>(logistics)));
    }

    /**
     * 通过主键查询单条数据，查询物流信息
     *
     * @return 单条数据
     */
    @GetMapping("{oid}")
    public ResponseEntity selectOne(@PathVariable Serializable oid) {

        Order order = orderService.getById(oid);
        ServiceOrder serviceOrder = serviceOrderService.getById(order.getSid());

        Logistics logistics;
        //判断是归还还是发货
        if (serviceOrder.getReturnId() != null) {
            //归还
            logistics = logisticsService.getById(serviceOrder.getReturnId());
        } else {
            //发货
            logistics = logisticsService.getById(serviceOrder.getDeliverId());
        }

        //快递公司编码
        Express express = expressService.getById(logistics.getEid());

        //通过存储物流信息到数据库查询
        /*String traces = logistics.getTraces();
        JSONArray array = JSON.parseArray(traces);
        return ResponseEntity.success(array);*/

        //快递鸟在途监控 即时查询
        KdniaoSubscribe subscribe = new KdniaoSubscribe();
        String traces = subscribe.orderOnlineByJson(express.getCode(), logistics.getTrackingNo(), order.getPhone());
        JSONArray array = JSON.parseArray(traces);

        return ResponseEntity.success(array);
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity insert(@RequestBody LogisticsVo vo) {
        Logistics logistics = new Logistics();
        BeanUtils.copyProperties(vo, logistics);
        return ResponseEntity.success(this.logisticsService.save(logistics));
    }

    /**
     * 修改数据
     *
     * @param logistics 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity update(@RequestBody Logistics logistics) {
        return ResponseEntity.success(this.logisticsService.updateById(logistics));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity delete(@RequestParam("idList") List<Long> idList) {
        return ResponseEntity.success(this.logisticsService.removeByIds(idList));
    }
}
