package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (UserInfo)表实体类
 *
 * @author makejava
 * @since 2021-04-15 10:37:47
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    //用户ID
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //用户姓名
    private String name;
    //用户性别
    private String sex;
    //用户年龄
    private Integer age;
    //用户头像
    private String avatar;
    //VIP等级ID
    private Integer vid;
    //支付宝用户ID
    private String userId;
    //用户余额
    private Double money;
    //手机号
    private String phone;
    //状态（0：封禁；1:正常）
    private Integer state;
    //Vip到期时间
    private Date vipExpirationTime;
}
