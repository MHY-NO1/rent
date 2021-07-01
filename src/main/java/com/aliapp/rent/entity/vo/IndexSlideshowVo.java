package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexSlideshowVo implements Serializable {
    private static final long serialVersionUID = 9145072721637132016L;

    //首页轮播图ID
    private Integer id;
    //轮播图地址
    private String url;
    //图片对应分类ID
    private Integer cid;
    //图片对应商品ID
    private Integer pid;
}
