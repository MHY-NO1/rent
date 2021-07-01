package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Category)表实体类
 *
 * @author makejava
 * @since 2021-04-06 10:05:50
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category{
    //分类ID
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //分类名
    private String title;
}
