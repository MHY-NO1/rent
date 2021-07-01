package com.aliapp.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aliapp.rent.dao.UserCategoryApplyDao;
import com.aliapp.rent.entity.po.UserCategoryApply;
import com.aliapp.rent.service.UserCategoryApplyService;
import org.springframework.stereotype.Service;

/**
 * (UserCategoryApply)表服务实现类
 *
 * @author makejava
 * @since 2021-04-20 14:22:51
 */
@Service("userCategoryApplyService")
public class UserCategoryApplyServiceImpl extends ServiceImpl<UserCategoryApplyDao, UserCategoryApply> implements UserCategoryApplyService {

}
