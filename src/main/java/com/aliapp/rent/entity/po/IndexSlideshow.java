package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (IndexSlideshow)表实体类
 *
 * @author makejava
 * @since 2021-04-06 13:22:44
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexSlideshow {
    //首页轮播图ID
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //轮播图地址
    private String url;
    //图片对应分类ID
    private Integer cid;
    //图片对应商品ID
    private Integer pid;
}
