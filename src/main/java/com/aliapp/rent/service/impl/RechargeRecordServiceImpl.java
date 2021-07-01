package com.aliapp.rent.service.impl;

import com.aliapp.rent.entity.vo.RechargeRecordVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aliapp.rent.dao.RechargeRecordDao;
import com.aliapp.rent.entity.po.RechargeRecord;
import com.aliapp.rent.service.RechargeRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (RechargeRecord)表服务实现类
 *
 * @author makejava
 * @since 2021-06-26 14:32:48
 */
@Service("rechargeRecordService")
public class RechargeRecordServiceImpl extends ServiceImpl<RechargeRecordDao, RechargeRecord> implements RechargeRecordService {

    @Resource
    RechargeRecordDao rechargeRecordDao;

    @Override
    public List<RechargeRecordVo> queryAmountByVid(Wrapper wrapper) {
        return rechargeRecordDao.queryAmountByVid(wrapper);
    }
}
