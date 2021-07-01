package com.aliapp.rent.dao;

import com.aliapp.rent.entity.po.Search;
import com.aliapp.rent.entity.vo.SearchVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (Search)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-14 13:34:27
 */
public interface SearchDao extends BaseMapper<Search> {
    @Select("SELECT `name`, COUNT(*) num from search ${ew.customSqlSegment}")
    List<SearchVo> queryHistory(@Param(Constants.WRAPPER) Wrapper<Search> wrapper);
}
