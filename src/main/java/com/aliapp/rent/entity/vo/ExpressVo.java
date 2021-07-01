package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpressVo implements Serializable {
    private static final long serialVersionUID = 6021893917495170878L;

    //ID
    private Integer id;
    //快递公司名称
    private String name;
    //快递公司编码
    private String code;

}
