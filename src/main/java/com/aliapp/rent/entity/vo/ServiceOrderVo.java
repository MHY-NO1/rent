package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceOrderVo implements Serializable {
    private static final long serialVersionUID = 6802868639086031350L;

    private Integer id;
    //外部订单号
    private String outBizNo;
    //购买者UID
    private String buyerId;
    //订单创建时间
    private Date orderCreateTime;
    //订单修改时间
    private Date orderModifiedTime;
    //订单详情页地址
    private String merchantOrderLinkPage;
    //发货物流ID
    private Integer deliverId;
    //归还物流ID
    private Integer returnId;

    //formId
    private String formId;
    //uid
    private Integer uid;
    //订单ID
    private Integer oid;

    //判断是发货还是归还
    private String type;

    //物流公司名称
    private String logisticsCode;
    //物流单号
    private String trackingNo;
}
