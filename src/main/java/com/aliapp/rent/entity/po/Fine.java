package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Fine)表实体类
 *
 * @author makejava
 * @since 2021-06-17 15:13:35
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fine {
    //逾期罚金
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //元/天
    private Double money;
    //租价比例/天
    private Double percent;
    //类型（0：自定义；1：租价比例）
    private Integer state;

}
