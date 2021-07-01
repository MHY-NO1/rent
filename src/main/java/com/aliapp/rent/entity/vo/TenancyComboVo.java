package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenancyComboVo implements Serializable {
    private static final long serialVersionUID = -827217636343872837L;

    //租期、套餐关联  ID
    private Integer id;
    //总租金
    private Double rent;
    //库存
    private Integer inventory;
    //套餐ID
    private String cid;
    //租期天数
    private Integer tid;
    //产品ID
    private Integer pid;
    //押金
    private Double deposite;
    //买断价格
    private Double buyOutAmount;

    //传参数据
    private List<ComboVo> list;
    //总租期
    private String tenancy;

    //获取编辑数据
    private List<EditComboVo> editList;
}
