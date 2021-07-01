package com.aliapp.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aliapp.rent.dao.CommissionDao;
import com.aliapp.rent.entity.po.Commission;
import com.aliapp.rent.service.CommissionService;
import org.springframework.stereotype.Service;

/**
 * (Commission)表服务实现类
 *
 * @author makejava
 * @since 2021-05-15 13:19:43
 */
@Service("commissionService")
public class CommissionServiceImpl extends ServiceImpl<CommissionDao, Commission> implements CommissionService {

}
