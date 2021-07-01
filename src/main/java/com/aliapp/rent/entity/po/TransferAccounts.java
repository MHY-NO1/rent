package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (TransferAccounts)表实体类
 *
 * @author makejava
 * @since 2021-06-29 11:06:43
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferAccounts {
    //商家单笔转账表
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //转账金额
    private Double transferAmount;
    //商户端的唯一订单号
    private String outBizNo;
    //创建时间
    private Date createTime;
    //收款人ID
    private Integer uid;
    //状态（0：租金转账；1：续租转账；2：买断转账；3：逾期转账）
    private Integer state;
    //订单ID
    private Integer oid;

}
