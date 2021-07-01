package com.aliapp.rent.entity.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVo implements Serializable {
    private static final long serialVersionUID = -6790885736785517626L;

    //产品ID
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
    //租期（ID的集合，使用逗号分割）
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
    //发货方式
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

    //综合1、销量2、价格3
    private Integer commodityType;
    //搜索框值
    private String searchValue;

    //选中的分类
    private String selectValue;

    //过滤
    private String brand;
    private String logistics;
    private Double minRent;
    private Double maxRent;
    private Double minDeposit;
    private Double maxDeposit;

    //二级分类名
    private String cname;
}
