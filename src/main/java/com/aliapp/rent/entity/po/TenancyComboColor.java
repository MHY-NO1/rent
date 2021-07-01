package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (TenancyComboColor)表实体类
 *
 * @author makejava
 * @since 2021-04-07 11:14:49
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenancyComboColor{
    //租期、套餐、颜色关联ID
    @TableId(value = "id",type = IdType.AUTO)
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
