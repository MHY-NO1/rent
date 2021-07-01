package com.aliapp.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aliapp.rent.dao.CategoryDao;
import com.aliapp.rent.entity.po.Category;
import com.aliapp.rent.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * (Category)表服务实现类
 *
 * @author makejava
 * @since 2021-04-06 10:05:57
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {

}
