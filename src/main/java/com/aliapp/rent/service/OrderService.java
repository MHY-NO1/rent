package com.aliapp.rent.service;

import com.aliapp.rent.entity.po.Order;
import com.aliapp.rent.entity.vo.OrderVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * (Order)表服务接口
 *
 * @author makejava
 * @since 2021-04-13 16:57:49
 */
public interface OrderService extends IService<Order> {
    IPage<OrderVo> queryOrder(Page<OrderVo> page, Wrapper<Order> wrapper);

    IPage<OrderVo> queryMyRent(Page<OrderVo> page, Wrapper<Order> wrapper);

    List<OrderVo> queryOrderByCategoryId(Wrapper<Order> wrapper);

    List<OrderVo> queryProductOrderNum(Wrapper<Order> wrapper);

    List<OrderVo> queryOrderCount(Wrapper<Order> wrapper);
}
