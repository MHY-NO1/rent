package com.aliapp.rent.service;

import com.aliapp.rent.entity.vo.RechargeRecordVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.aliapp.rent.entity.po.RechargeRecord;

import java.util.List;

/**
 * (RechargeRecord)表服务接口
 *
 * @author makejava
 * @since 2021-06-26 14:32:48
 */
public interface RechargeRecordService extends IService<RechargeRecord> {
    List<RechargeRecordVo> queryAmountByVid(Wrapper<RechargeRecord> wrapper);
}
