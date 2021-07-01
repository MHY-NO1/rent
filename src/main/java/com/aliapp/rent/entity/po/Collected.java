package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Collected)表实体类
 *
 * @author makejava
 * @since 2021-04-13 11:39:26
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Collected{
    //用户收藏表
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //商品ID
    private Integer pid;
    //0：商品收藏；1：店铺收藏
    private Object state;
    //用户ID
    private Integer uid;
    //店铺ID
    private Integer sid;
}
