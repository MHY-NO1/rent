package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (RechargeRecord)表实体类
 *
 * @author makejava
 * @since 2021-06-26 14:32:47
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RechargeRecord {
    //会员充值记录表
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //用户ID
    private Integer uid;
    //会员ID
    private Integer vid;
    //充值月数（月）
    private Integer month;
    //充值时间
    private Date createTime;
    //充值金额
    private Double amount;
}
