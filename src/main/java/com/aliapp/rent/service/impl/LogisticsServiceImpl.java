package com.aliapp.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aliapp.rent.dao.LogisticsDao;
import com.aliapp.rent.entity.po.Logistics;
import com.aliapp.rent.service.LogisticsService;
import org.springframework.stereotype.Service;

/**
 * (Logistics)表服务实现类
 *
 * @author makejava
 * @since 2021-05-07 10:59:37
 */
@Service("logisticsService")
public class LogisticsServiceImpl extends ServiceImpl<LogisticsDao, Logistics> implements LogisticsService {

}
