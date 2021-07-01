package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandVo implements Serializable {
    private static final long serialVersionUID = 5952541040645974293L;

    private Integer id;
    //品牌名
    private String name;
    //子分类ID
    private Integer clid;

    //商品id
    private Integer pid;
}
