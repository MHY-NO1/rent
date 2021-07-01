package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (UserCategoryApply)表实体类
 *
 * @author makejava
 * @since 2021-04-20 14:22:50
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCategoryApply {
    //用户申请分类表
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //分类名
    private String name;
    //分类父ID
    private Integer pid;
    //该类目申请数量
    private Integer num;
}
