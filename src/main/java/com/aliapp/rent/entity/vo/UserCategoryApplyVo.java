package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCategoryApplyVo implements Serializable {
    private static final long serialVersionUID = 6240714354529417494L;

    private Integer id;
    //分类名
    private String name;
    //分类父ID
    private Integer pid;
    //该类目申请数量
    private Integer num;

    //子分类list
    private List<String> list;
}
