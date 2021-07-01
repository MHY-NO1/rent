package com.aliapp.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aliapp.rent.dao.BizApplyDao;
import com.aliapp.rent.entity.po.BizApply;
import com.aliapp.rent.service.BizApplyService;
import org.springframework.stereotype.Service;

/**
 * (BizApply)表服务实现类
 *
 * @author makejava
 * @since 2021-05-19 11:05:22
 */
@Service("bizApplyService")
public class BizApplyServiceImpl extends ServiceImpl<BizApplyDao, BizApply> implements BizApplyService {

}
