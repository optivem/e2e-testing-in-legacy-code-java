package com.optivem.eshop.systemtest.core.clients.api.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class GetOrderResponse {
    private String orderNumber;
    private String sku;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal originalPrice;
    private BigDecimal discountRate;
    private BigDecimal discountAmount;
    private BigDecimal subtotalPrice;
    private BigDecimal taxRate;
    private BigDecimal taxAmount;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private String country;
}

