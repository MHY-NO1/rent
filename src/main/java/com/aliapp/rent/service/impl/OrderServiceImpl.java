package com.aliapp.rent.service.impl;

import com.aliapp.rent.dao.OrderDao;
import com.aliapp.rent.entity.po.Order;
import com.aliapp.rent.entity.vo.OrderVo;
import com.aliapp.rent.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Order)表服务实现类
 *
 * @author makejava
 * @since 2021-04-13 16:57:49
 */
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, Order> implements OrderService {

    @Resource
    OrderDao orderDao;

    @Override
    public IPage<OrderVo> queryOrder(Page<OrderVo> page, Wrapper<Order> wrapper) {
        return orderDao.queryOrder(page, wrapper);
    }

    @Override
    public IPage<OrderVo> queryMyRent(Page<OrderVo> page, Wrapper<Order> wrapper) {
        return orderDao.queryMyRent(page, wrapper);
    }

    @Override
    public List<OrderVo> queryOrderByCategoryId(Wrapper<Order> wrapper) {
        return orderDao.queryOrderByCategoryId(wrapper);
    }

    @Override
    public List<OrderVo> queryProductOrderNum(Wrapper<Order> wrapper) {
        return orderDao.queryProductOrderNum(wrapper);
    }

    @Override
    public List<OrderVo> queryOrderCount(Wrapper<Order> wrapper) {
        return orderDao.queryOrderCount(wrapper);
    }
}
