package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryListVo implements Serializable {
    private static final long serialVersionUID = -2665321062103943108L;

    //分类列表ID
    private Integer id;
    //名称
    private String name;
    //图片地址
    private String picUrl;
    //父类id
    private Integer cid;
    //状态
    private Integer state;

    //唯一标识
    private Integer code;

    public CategoryListVo(String name, Integer code) {
        this.name = name;
        this.code = code;
    }
}
