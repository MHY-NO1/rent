package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Logistics)表实体类
 *
 * @author makejava
 * @since 2021-05-07 10:59:36
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Logistics {
    //物流信息ID
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //物流单号
    private String trackingNo;
    //物流公司ID
    private Integer eid;
    //物流信息
    private String traces;
    //物流状态
    private String state;
    //商户ID
    private String businessId;
}
