package com.aliapp.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aliapp.rent.dao.BrandDao;
import com.aliapp.rent.entity.po.Brand;
import com.aliapp.rent.service.BrandService;
import org.springframework.stereotype.Service;

/**
 * (Brand)表服务实现类
 *
 * @author makejava
 * @since 2021-04-14 16:40:50
 */
@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, Brand> implements BrandService {

}
