package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (ServiceOrder)表实体类
 *
 * @author makejava
 * @since 2021-04-26 11:23:40
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceOrder {
    //服务订单ID
    @TableId(value = "id", type = IdType.AUTO)
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

}
