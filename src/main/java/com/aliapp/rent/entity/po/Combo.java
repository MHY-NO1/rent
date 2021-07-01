package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Combo)表实体类
 *
 * @author makejava
 * @since 2021-04-07 11:21:04
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Combo{
    //套餐ID
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //套餐名
    private String name;
    //产品ID
    private Integer pid;
}
