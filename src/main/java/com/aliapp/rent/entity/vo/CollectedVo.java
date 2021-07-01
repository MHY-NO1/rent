package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectedVo implements Serializable {
    private static final long serialVersionUID = 5320657547992864479L;

    //用户收藏表
    private Integer id;
    //商品ID
    private Integer pid;
    //0：商品收藏；1：店铺收藏
    private Object state;
    //用户ID
    private Integer uid;
    //店铺ID
    private Integer sid;

    //图片
    private String picUrl;
    //标题
    private String title;
}
