package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminVo implements Serializable {
    private static final long serialVersionUID = -989723295145132274L;

    private Integer id;
    //账号
    private String uname;
    //密码
    private String pwd;
    //姓名
    private String name;
}
