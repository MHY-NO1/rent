package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogisticsVo implements Serializable {
    private static final long serialVersionUID = -4614735797161373267L;

    private Integer id;
    //物流单号
    private String trackingNo;
    //物流公司ID
    private Integer eid;
    //物流信息
    private String traces;
    //物流状态
    private String state;
    //商户ID
    private String businessId;
}
