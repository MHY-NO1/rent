package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVo implements Serializable {
    private static final long serialVersionUID = -7982285504259014565L;

    //用户ID
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

    //授权码
    private String authCode;
    //加密手机号
    private String encryptedData;

    //pid
    private Integer pid;
    //充值会员交易号
    private String tradeNo;
    //充值月数
    private Integer month;
    //充值金额
    private Double amount;
}
