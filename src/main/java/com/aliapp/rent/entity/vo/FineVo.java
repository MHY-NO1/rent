package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FineVo implements Serializable {
    private static final long serialVersionUID = -6646502860986041423L;

    private Integer id;
    //元/天
    private Double money;
    //租价比例/天
    private Double percent;
    //类型（0：自定义；1：租价比例）
    private Integer state;
}
