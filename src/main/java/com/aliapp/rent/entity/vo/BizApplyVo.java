package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BizApplyVo implements Serializable {
    private static final long serialVersionUID = -1665529998760635909L;

    private Integer id;
    //申请人姓名
    private String name;
    //手机号
    private String phone;
    //公司名称
    private String company;
    //所在城市
    private String city;
    //用户ID
    private Integer uid;
    //申请状态（0：待审核；1：通过；2：驳回）
    private Integer state;
}
