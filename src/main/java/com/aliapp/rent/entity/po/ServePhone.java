package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (ServePhone)表实体类
 *
 * @author makejava
 * @since 2021-05-18 14:23:46
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServePhone{
    //客服电话ID
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //客服电话
    private String phone;

}
