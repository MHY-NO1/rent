package com.aliapp.rent.dao;

import com.aliapp.rent.entity.po.Order;
import com.aliapp.rent.entity.vo.OrderVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (Order)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-13 16:57:49
 */
public interface OrderDao extends BaseMapper<Order> {
    @Select("select o.*,tc.cid,tc.tid,tc.title,tc.pic_url from `order` o left join (select t.*,p.title,p.pic_url from" +
            " product p right join tenancy_combo t on p.id=t.pid ) tc on o.tcid=tc.id ${ew.customSqlSegment}")
    IPage<OrderVo> queryOrder(IPage<OrderVo> page, @Param(Constants.WRAPPER) Wrapper<Order> wrapper);

    @Select("select o.*,tc.cid,tc.tid,tc.title,tc.pic_url,tc.publisher_id from `order` o left join (select t.*,p" +
            ".publisher_id,p.title,p.pic_url from product p right join tenancy_combo t on p.id=t.pid ) tc on o" +
            ".tcid=tc.id ${ew.customSqlSegment}")
    IPage<OrderVo> queryMyRent(IPage<OrderVo> page, @Param(Constants.WRAPPER) Wrapper<Order> wrapper);

    @Select("select ifnull(count(*),0) count,ifnull(sum(r.rent_amount),0) rent_amount_sum,ifnull(sum(r.money),0) money,ifnull" +
            "(sum(o2.expire_num),0) expire_num,r.cid category_id from " +
            "(select o1.*,p1.cid from (select o.*,m.money from `order` o left join commission_list m on o.id=m.oid) o1 left " +
            "join (SELECT cl.cid,p.id from product p left join category_list cl on p.clid=cl.id)p1 on o1.pid=p1.id) r left join" +
            " (select id,1 expire_num from `order` where compensate_amount>0) o2 on r.id=o2.id ${ew.customSqlSegment}")
    List<OrderVo> queryOrderByCategoryId(@Param(Constants.WRAPPER) Wrapper<Order> wrapper);

    @Select("select ifnull(count(*),0) count,ifnull(sum(r.rent_amount),0) rent_amount_sum,ifnull(sum(r.money),0) money,ifnull" +
            "(sum(o2.expire_num),0) expire_num,r.pname,r.pid from " +
            "(SELECT o1.*,p.name pname from (select o.*,m.money from `order` o left join commission_list m on o.id=m.oid) o1 " +
            "left join product p on o1.pid = p.id) r left join (select id,1 expire_num " +
            "from `order` where compensate_amount>0) o2 on r.id=o2.id ${ew.customSqlSegment} ")
    List<OrderVo> queryProductOrderNum(@Param(Constants.WRAPPER) Wrapper<Order> wrapper);

    @Select("select ifnull(count(*),0) count,ifnull(sum(r.rent_amount),0) rent_amount_sum,ifnull(sum(r.money),0) money,ifnull" +
            "(sum(o2.expire_num),0) expire_num from " +
            "(select o.*,m.money from `order` o left join commission_list m on o.id=m.oid) r left join (select id,1 expire_num " +
            "from `order` where compensate_amount>0) o2 on r.id=o2.id ${ew.customSqlSegment} ")
    List<OrderVo> queryOrderCount(@Param(Constants.WRAPPER) Wrapper<Order> wrapper);
}
