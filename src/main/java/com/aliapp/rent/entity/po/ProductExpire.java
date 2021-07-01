package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (ProductExpire)表实体类
 *
 * @author makejava
 * @since 2021-06-17 14:43:36
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductExpire {
    //商品过期设置（周期内，销量不足，商品下架）
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //周期
    private Integer day;
    //销量
    private Integer sales;
}
