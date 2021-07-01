package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchVo implements Serializable {
    private static final long serialVersionUID = 4406190131897337583L;

    //搜索ID
    private Integer id;
    //搜索值
    private String name;
    //用户ID
    private Integer uid;

    //搜索框的值
    private String searchValue;

}
