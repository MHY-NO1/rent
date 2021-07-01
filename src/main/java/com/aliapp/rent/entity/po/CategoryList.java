package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (CategoryList)表实体类
 *
 * @author makejava
 * @since 2021-04-06 14:00:25
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryList {
    //分类列表ID
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //名称
    private String name;
    //图片地址
    private String picUrl;
    //父类id
    private Integer cid;
    //状态
    private Integer state;
}