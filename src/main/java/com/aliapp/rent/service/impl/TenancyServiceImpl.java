package com.aliapp.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aliapp.rent.dao.TenancyDao;
import com.aliapp.rent.entity.po.Tenancy;
import com.aliapp.rent.service.TenancyService;
import org.springframework.stereotype.Service;

/**
 * (Tenancy)表服务实现类
 *
 * @author makejava
 * @since 2021-04-07 11:16:01
 */
@Service("tenancyService")
public class TenancyServiceImpl extends ServiceImpl<TenancyDao, Tenancy> implements TenancyService {

}
