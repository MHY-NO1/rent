package com.aliapp.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aliapp.rent.dao.ProductExpireDao;
import com.aliapp.rent.entity.po.ProductExpire;
import com.aliapp.rent.service.ProductExpireService;
import org.springframework.stereotype.Service;

/**
 * (ProductExpire)表服务实现类
 *
 * @author makejava
 * @since 2021-06-17 14:43:36
 */
@Service("productExpireService")
public class ProductExpireServiceImpl extends ServiceImpl<ProductExpireDao, ProductExpire> implements ProductExpireService {

}
