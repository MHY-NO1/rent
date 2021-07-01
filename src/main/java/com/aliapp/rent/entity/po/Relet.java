package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (Relet)表实体类
 *
 * @author makejava
 * @since 2021-04-28 15:59:51
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Relet {
    //续租表ID
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //续租金额
    private Double amount;
    //续租订单
    private Integer oid;
    //续租天数
    private Integer day;
    //续租时间
    private Date createTime;
}
