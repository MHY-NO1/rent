package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tenancy implements Serializable {
    private static final long serialVersionUID = 5404650342677193456L;

    //ID
    private Integer id;
    //租期
    private Integer days;
}
