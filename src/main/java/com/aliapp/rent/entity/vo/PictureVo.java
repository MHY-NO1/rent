package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PictureVo implements Serializable {
    private static final long serialVersionUID = -3060523001797562421L;

    //ID
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

    //图片地址
    private String filePath;

}
