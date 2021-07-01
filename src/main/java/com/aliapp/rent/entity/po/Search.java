package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Search)表实体类
 *
 * @author makejava
 * @since 2021-04-14 13:34:26
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Search {
    //搜索ID
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //搜索名称
    private String name;
    //用户ID
    private Integer uid;
}
