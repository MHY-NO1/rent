package com.aliapp.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aliapp.rent.dao.CommissionListDao;
import com.aliapp.rent.entity.po.CommissionList;
import com.aliapp.rent.service.CommissionListService;
import org.springframework.stereotype.Service;

/**
 * (CommissionList)表服务实现类
 *
 * @author makejava
 * @since 2021-06-28 17:34:50
 */
@Service("commissionListService")
public class CommissionListServiceImpl extends ServiceImpl<CommissionListDao, CommissionList> implements CommissionListService {

}
