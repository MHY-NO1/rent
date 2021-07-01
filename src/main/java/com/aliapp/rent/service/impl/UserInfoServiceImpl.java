package com.aliapp.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aliapp.rent.dao.UserInfoDao;
import com.aliapp.rent.entity.po.UserInfo;
import com.aliapp.rent.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * (UserInfo)表服务实现类
 *
 * @author makejava
 * @since 2021-04-15 10:37:47
 */
@Service("userInfoService")
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfo> implements UserInfoService {

}
