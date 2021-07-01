package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommissionVo implements Serializable {
    private static final long serialVersionUID = 2292701729686904052L;

    private Integer id;
    //抽成比例（%）
    private Double percentage;
}
