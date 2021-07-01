package com.aliapp.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aliapp.rent.dao.CategoryListDao;
import com.aliapp.rent.entity.po.CategoryList;
import com.aliapp.rent.service.CategoryListService;
import org.springframework.stereotype.Service;

/**
 * (CategoryList)表服务实现类
 *
 * @author makejava
 * @since 2021-04-06 14:00:26
 */
@Service("categoryListService")
public class CategoryListServiceImpl extends ServiceImpl<CategoryListDao, CategoryList> implements CategoryListService {

}
