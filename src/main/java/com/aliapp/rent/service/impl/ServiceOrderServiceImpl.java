package com.aliapp.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aliapp.rent.dao.ServiceOrderDao;
import com.aliapp.rent.entity.po.ServiceOrder;
import com.aliapp.rent.service.ServiceOrderService;
import org.springframework.stereotype.Service;

/**
 * (ServiceOrder)表服务实现类
 *
 * @author makejava
 * @since 2021-04-26 11:23:41
 */
@Service("serviceOrderService")
public class ServiceOrderServiceImpl extends ServiceImpl<ServiceOrderDao, ServiceOrder> implements ServiceOrderService {

}
