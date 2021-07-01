package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServePhoneVo implements Serializable {
    private static final long serialVersionUID = 7784097586429458806L;

    private Integer id;
    //客服电话
    private String phone;
}
