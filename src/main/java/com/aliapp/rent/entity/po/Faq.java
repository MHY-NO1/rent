package com.aliapp.rent.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Faq)表实体类
 *
 * @author makejava
 * @since 2021-05-22 11:54:56
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Faq {
    //常见问题表ID
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //标题
    private String title;
    //解答
    private String answer;
    //类型（0：订单问题；1：押金问题；2：物流问题；3：故障维修问题；4：其他问题）
    private Integer type;
}
