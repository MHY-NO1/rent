package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferAccountsVo implements Serializable {
    private static final long serialVersionUID = 3561270916598190392L;

    private Integer id;
    //转账金额
    private Double transferAmount;
    //商户端的唯一订单号
    private String outBizNo;
    //创建时间
    private Date createTime;
    //收款人ID
    private Integer uid;
    //状态（0：租金转账；1：续租转账；2：买断转账；3：逾期转账）
    private Integer state;
    //订单ID
    private Integer oid;
}
