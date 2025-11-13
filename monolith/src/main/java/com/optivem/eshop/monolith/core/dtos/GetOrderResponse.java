package com.optivem.eshop.monolith.core.dtos;

import com.optivem.eshop.monolith.core.entities.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class GetOrderResponse {
    private String orderNumber;
    private String sku;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private String country;
}