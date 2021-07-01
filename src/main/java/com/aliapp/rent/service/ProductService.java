package com.aliapp.rent.service;

import com.aliapp.rent.entity.po.Product;
import com.aliapp.rent.entity.vo.ProductVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * (Product)表服务接口
 *
 * @author makejava
 * @since 2021-04-07 09:33:45
 */
public interface ProductService extends IService<Product> {
    IPage<ProductVo> queryProduct(Page<ProductVo> page, Wrapper<Product> wrapper);
}
