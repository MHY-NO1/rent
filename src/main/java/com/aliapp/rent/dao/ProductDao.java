package com.aliapp.rent.dao;

import com.aliapp.rent.entity.po.Product;
import com.aliapp.rent.entity.vo.ProductVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * (Product)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-07 09:33:45
 */
public interface ProductDao extends BaseMapper<Product> {
    @Select("select a.*,b.* from(select p.*,uc.level from " +
            "product p left join (select u.*,v.level from " +
            "user_info u left join vip v on u.vid=v.id) uc " +
            "on p.publisher_id = uc.id) a left join " +
            "(select cl.*,c.title from category_list cl left join category c on cl.cid=c.id) b" +
            " on a.clid=b.id ${ew.customSqlSegment}")
    IPage<ProductVo> queryProduct(IPage<ProductVo> page, @Param(Constants.WRAPPER) Wrapper<Product> wrapper);
}
