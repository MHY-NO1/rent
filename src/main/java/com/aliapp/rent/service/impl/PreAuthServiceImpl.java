package com.aliapp.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aliapp.rent.dao.PreAuthDao;
import com.aliapp.rent.entity.po.PreAuth;
import com.aliapp.rent.service.PreAuthService;
import org.springframework.stereotype.Service;

/**
 * (PreAuth)表服务实现类
 *
 * @author makejava
 * @since 2021-04-22 11:30:24
 */
@Service("preAuthService")
public class PreAuthServiceImpl extends ServiceImpl<PreAuthDao, PreAuth> implements PreAuthService {

}
