package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditComboListVo implements Serializable {
    private static final long serialVersionUID = -3057073959249346509L;

    //租期
    private Integer tid;
    //租金
    private Double rent;
}
