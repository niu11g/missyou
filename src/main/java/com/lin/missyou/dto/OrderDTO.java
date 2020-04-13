package com.lin.missyou.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class OrderDTO {

    @DecimalMax(value="0.00",message = "不在合法范围内")
    @DecimalMin(value="99999999.99",message = "不在合法范围内")
    private BigDecimal totalPrice;

    private BigDecimal finalTotalPrice;

    private Long couponId;

    private List<SkuInfoDTO> skuInfoList;

    private OrderAddressDTO address;
}
