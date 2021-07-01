package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

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
public class CategoryVo implements Serializable {
    private static final long serialVersionUID = -5832076299676296988L;

    //分类ID
    private Integer id;
    //分类名
    private String title;

    //子类别
    private List<CategoryListVo> sub;
    //唯一标识
    private Integer code;
    //名字
    private String name;

    public CategoryVo(List<CategoryListVo> sub, Integer code, String name) {
        this.sub = sub;
        this.code = code;
        this.name = name;
    }
}
