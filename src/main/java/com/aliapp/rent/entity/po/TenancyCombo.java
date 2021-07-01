package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (TenancyCombo)表实体类
 *
 * @author makejava
 * @since 2021-04-10 13:08:01
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenancyCombo{
    //租期、套餐关联  ID
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //总租金
    private Double rent;
    //库存
    private Integer inventory;
    //套餐ID
    private String cid;
    //租期ID
    private Integer tid;
    //产品ID
    private Integer pid;
    //押金
    private Double deposite;
    //买断价格
    private Double buyOutAmount;
}
