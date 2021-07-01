package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReletVo implements Serializable {
    private static final long serialVersionUID = -6671426252094145471L;

    //续租表ID
    private Integer id;
    //续租金额
    private Double amount;
    //续租订单
    private Integer oid;
    //续租天数
    private Integer day;
    //续租时间
    private Date createTime;

    //tradeNo
    private String tradeNo;
}
