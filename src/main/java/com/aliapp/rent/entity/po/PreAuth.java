package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreAuth {
    //预授权表ID
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //预授权编号
    private String authNo;
    //付款者userid
    private String payerUserId;
    //支付状态（0：初始；1：成功；2：关闭）
    private Integer status;
    //信用冻结金额
    private Double creditAmount;
    //资金冻结金额
    private Double fundAmount;
    //冻结总金额
    private Double amount;
    //预授权订单号
    private String orderNo;
    //订单标题
    private String title;
    //请求号
    private String reqNo;
}
