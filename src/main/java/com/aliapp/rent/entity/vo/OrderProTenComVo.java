package com.aliapp.rent.entity.vo;

import com.aliapp.rent.entity.po.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProTenComVo implements Serializable {
    private static final long serialVersionUID = 7965518115858452183L;

    private Order order;
    private TenancyCombo tenancyCombo;
    private Product product;
    private PreAuth preAuth;
    private ServiceOrder serviceOrder;

    private List<Order> orderList;
}
