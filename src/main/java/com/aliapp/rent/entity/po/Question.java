package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Question)表实体类
 *
 * @author makejava
 * @since 2021-05-18 09:49:04
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    //问题ID
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    //问题标题
    private String name;
    //问题答案
    private String answer;
    //问题类型（0：常见问题；1：支付问题；2：设备维修；3：租赁保障）
    private Integer type;
}
