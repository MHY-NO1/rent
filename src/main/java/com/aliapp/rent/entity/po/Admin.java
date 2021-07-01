package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Admin)表实体类
 *
 * @author makejava
 * @since 2021-05-10 10:58:52
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin{
    //管理员ID
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //账号
    private String uname;
    //密码
    private String pwd;
    //姓名
    private String name;

}
