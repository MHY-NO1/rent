package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComboVo implements Serializable {
    private static final long serialVersionUID = -7647930935948679618L;

    //套餐ID
    private Integer id;
    //套餐名
    private String name;
    //产品ID
    private Integer pid;

    //租金List（根据天数）
    private List<Double> rents;
    //库存
    private Integer inventory;
    //买断金额
    private Double buyOutAmount;
    //押金
    private Double deposite;

}
