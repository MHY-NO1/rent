package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * (Product)表实体类
 *
 * @author makejava
 * @since 2021-04-07 09:33:45
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product{
    //产品ID
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    //产品种类ID
    private Integer clid;
    //出租人ID
    private Integer publisherId;
    //承租人ID
    private Integer accepterId;
    //产品标题
    private String title;
    //产品描述
    private String subtitle;
    //租期（例如3,7,30 ，使用逗号分割）
    private String tids;
    //创建日期
    private Date createDate;
    //标签（例如全新，包邮）
    private String tags;
    //产品名称（用于列表展示页）
    private String name;
    //产品总库存
    private Integer inventory;
    //列表展示图
    private String picUrl;
    //销量
    private Integer sales;
    //每日租价
    private Double priceDay;
    //发货方式（例如湖南长沙|顺丰快递/寄出包邮/归还自付）
    private String deliver;
    //套餐（用逗号分隔）
    private String cids;
    //品牌ID
    private Integer bid;
    //押金
    private Double deposite;
    //过期时间
    private Date expTime;
    //是否支持买断
    private Integer isBuyOut;
    //产品状态（0：下架；1：上架）
    private Integer state;
    //商品接口返回
    private String itemId;
    //商家电话
    private String phone;

}
