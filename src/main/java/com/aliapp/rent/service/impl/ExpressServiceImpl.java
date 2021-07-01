package com.aliapp.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aliapp.rent.dao.ExpressDao;
import com.aliapp.rent.entity.po.Express;
import com.aliapp.rent.service.ExpressService;
import org.springframework.stereotype.Service;

/**
 * (Express)表服务实现类
 *
 * @author makejava
 * @since 2021-05-08 13:06:45
 */
@Service("expressService")
public class ExpressServiceImpl extends ServiceImpl<ExpressDao, Express> implements ExpressService {

}
