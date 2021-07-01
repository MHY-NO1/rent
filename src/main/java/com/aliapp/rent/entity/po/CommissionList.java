package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (CommissionList)表实体类
 *
 * @author makejava
 * @since 2021-06-28 17:34:50
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommissionList {
    //抽成信息ID
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //抽成金额
    private Double money;
    //抽成订单ID
    private Integer oid;
}
