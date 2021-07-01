package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommissionListVo implements Serializable {
    private static final long serialVersionUID = 780567110242177495L;

    private Integer id;
    //抽成金额
    private Double money;
    //抽成订单ID
    private Integer oid;
}
