package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Vip)表实体类
 *
 * @author makejava
 * @since 2021-05-12 15:29:52
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vip {
    //VIP等级ID
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //VIP名称
    private String name;
    //VIP等级（0：普通会员；1：一级会员；2：二级会员；3：三级会员）
    private Integer level;
    //VIP每月租金
    private Double price;
    //可出租产品数量
    private Integer num;
    //商品上架时间（天）
    private Integer day;
    // 用户数量
    private Integer userNum;

}
