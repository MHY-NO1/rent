package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VipVo implements Serializable {
    private static final long serialVersionUID = -8795255069500289018L;
    private Integer id;
    //VIP名称
    private String name;
    //VIP等级（0：普通会员；1：一级会员；2：二级会员；3：三级会员）
    private Integer level;
    //VIP每月租金
    private Double price;
    //可出租产品数量
    private Integer num;
    //商品上架时间（天）
    private Integer day;
    // 用户数量
    private Integer userNum;

    //用户ID
    private Integer uid;
    // 充值的月数
    private Integer month;
    //升级金额
    private Double money;
    //tradeNo
    private String tradeNo;
}
