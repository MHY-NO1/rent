package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionVo implements Serializable {
    private static final long serialVersionUID = -3933250710045434945L;

    private Integer id;
    //问题标题
    private String name;
    //问题答案
    private String answer;
    //问题类型（0：常见问题；1：支付问题；2：设备维修；3：租赁保障）
    private Integer type;
}
