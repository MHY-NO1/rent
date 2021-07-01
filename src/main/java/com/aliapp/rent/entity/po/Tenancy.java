package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Tenancy)表实体类
 *
 * @author makejava
 * @since 2021-04-07 11:16:01
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tenancy {
    //ID
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //租期
    private Integer days;
}
