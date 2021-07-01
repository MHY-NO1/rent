package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Commission)表实体类
 *
 * @author makejava
 * @since 2021-05-15 13:19:42
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commission {
    //抽成ID
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //抽成比例（%）
    private Double percentage;
}
