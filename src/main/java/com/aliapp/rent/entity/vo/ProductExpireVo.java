package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductExpireVo implements Serializable {
    private static final long serialVersionUID = 6368697461193839829L;

    private Integer id;
    //周期
    private Integer day;
    //销量
    private Integer sales;
}
