package com.aliapp.rent.dao;

import com.aliapp.rent.entity.po.RechargeRecord;
import com.aliapp.rent.entity.vo.RechargeRecordVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (RechargeRecord)表数据库访问层
 *
 * @author makejava
 * @since 2021-06-26 14:32:48
 */
public interface RechargeRecordDao extends BaseMapper<RechargeRecord> {
    @Select("select vid,sum(amount) sum from recharge_record ${ew.customSqlSegment}")
    List<RechargeRecordVo> queryAmountByVid(@Param(Constants.WRAPPER) Wrapper<RechargeRecord> wrapper);
}
