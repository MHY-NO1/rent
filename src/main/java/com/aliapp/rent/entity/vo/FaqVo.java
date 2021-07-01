package com.aliapp.rent.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FaqVo implements Serializable {
    private static final long serialVersionUID = 7726486812880661743L;

    //常见问题表ID
    private Integer id;
    //标题
    private String title;
    //解答
    private String answer;
    //类型（0：订单问题；1：押金问题；2：物流问题；3：故障维修问题；4：其他问题）
    private Integer type;
}
