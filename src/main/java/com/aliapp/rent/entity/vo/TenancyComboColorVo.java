package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenancyComboColorVo implements Serializable {
    private static final long serialVersionUID = 5225739377411439352L;

    //租期、套餐、颜色关联ID
    private Integer id;
    //总租金
    private Double rent;
    //库存
    private Integer inventory;
    //套餐ID
    private Integer cid;
    //租期ID
    private Integer tid;
    //颜色ID
    private Integer picid;
    //产品ID
    private Integer pid;
}
