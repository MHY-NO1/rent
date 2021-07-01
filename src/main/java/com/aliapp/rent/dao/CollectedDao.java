package com.aliapp.rent.dao;

import com.aliapp.rent.entity.po.Collected;
import com.aliapp.rent.entity.vo.CollectedVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * (Collected)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-13 11:39:26
 */
public interface CollectedDao extends BaseMapper<Collected> {
    @Select("select c.*,p.pic_url,p.title from collected c left join product p on c.pid=p.id ${ew.customSqlSegment}")
    IPage<CollectedVo> queryCollected(IPage<CollectedVo> page, @Param(Constants.WRAPPER) Wrapper<Collected> wrapper);
}
