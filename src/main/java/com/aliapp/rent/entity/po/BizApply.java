package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (BizApply)表实体类
 *
 * @author makejava
 * @since 2021-05-19 11:05:21
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BizApply {
    //商家入驻申请ID
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //申请人姓名
    private String name;
    //手机号
    private String phone;
    //公司名称
    private String company;
    //所在城市
    private String city;
    //用户ID
    private Integer uid;
    //申请状态（0：待审核；1：通过；2：驳回）
    private Integer state;

}
