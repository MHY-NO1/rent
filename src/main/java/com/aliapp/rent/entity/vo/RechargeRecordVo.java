package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RechargeRecordVo implements Serializable {
    private static final long serialVersionUID = -7505238116555579165L;
    private Integer id;
    //用户ID
    private Integer uid;
    //会员ID
    private Integer vid;
    //充值月数（月）
    private Integer month;
    //充值时间
    private Date createTime;
    //充值金额
    private Double amount;

    //各级会员充值总金额
    private Double sum;
}
