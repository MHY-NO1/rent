package com.aliapp.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aliapp.rent.dao.AdminDao;
import com.aliapp.rent.entity.po.Admin;
import com.aliapp.rent.service.AdminService;
import org.springframework.stereotype.Service;

/**
 * (Admin)表服务实现类
 *
 * @author makejava
 * @since 2021-05-10 10:58:52
 */
@Service("adminService")
public class AdminServiceImpl extends ServiceImpl<AdminDao, Admin> implements AdminService {

}
