package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 编辑套餐时返回的实体
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditComboVo implements Serializable {
    private static final long serialVersionUID = 2292712803600329770L;

    //套餐名
    private String combo;
    //买断金额
    private Double buyOutAmount;
    //租期、租金列表
    private List<EditComboListVo> list;
    //库存
    private Integer inventory;
    //押金
    private Double deposite;

}
