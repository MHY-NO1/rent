package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Picture)表实体类
 *
 * @author makejava
 * @since 2021-04-07 11:20:37
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Picture {
    //ID
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //商品图片地址
    private String picUrl;
    //商品ID
    private Integer pid;
    //图片使用情况（0：轮播图，1：详情页长条图）
    private Integer state;
    //商品颜色
    private String color;
    //素材ID
    private String materialId;
    //素材key值
    private String materialKey;
}
