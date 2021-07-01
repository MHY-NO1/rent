package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Express)表实体类
 *
 * @author makejava
 * @since 2021-05-08 13:06:44
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Express {
    //ID
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //快递公司名称
    private String name;
    //快递公司编码
    private String code;
}
