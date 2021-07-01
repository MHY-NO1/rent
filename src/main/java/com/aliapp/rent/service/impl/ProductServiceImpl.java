package com.aliapp.rent.service.impl;

import com.aliapp.rent.dao.ProductDao;
import com.aliapp.rent.entity.po.Product;
import com.aliapp.rent.entity.vo.ProductVo;
import com.aliapp.rent.service.ProductService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (Product)表服务实现类
 *
 * @author makejava
 * @since 2021-04-07 09:33:45
 */
@Service("productService")
public class ProductServiceImpl extends ServiceImpl<ProductDao, Product> implements ProductService {

    @Resource
    ProductDao productDao;

    @Override
    public IPage<ProductVo> queryProduct(Page<ProductVo> page, Wrapper<Product> wrapper) {
        return productDao.queryProduct(page, wrapper);
    }
}

