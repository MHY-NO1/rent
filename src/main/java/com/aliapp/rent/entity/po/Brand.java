package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Brand)表实体类
 *
 * @author makejava
 * @since 2021-04-14 16:40:50
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Brand{
    //品牌ID
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //品牌名
    private String name;
    //分类ID
    private Integer clid;
}
