package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeVo implements Serializable {
    private static final long serialVersionUID = -907209888254467077L;

    private Integer id;
    private Integer pid;
    private String name;
    private String isParent;
}
