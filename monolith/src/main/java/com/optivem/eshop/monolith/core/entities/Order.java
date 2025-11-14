package com.optivem.eshop.monolith.core.entities;

import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;

@Data
public class Order {
    private String orderNumber;
    private Instant orderTimestamp;
    private String country;

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

    public Order(String orderNumber, Instant orderTimestamp, String country,
                 String sku, int quantity, BigDecimal unitPrice, BigDecimal originalPrice,
                 BigDecimal discountRate, BigDecimal discountAmount, BigDecimal subtotalPrice,
                 BigDecimal taxRate, BigDecimal taxAmount, BigDecimal totalPrice, OrderStatus status) {
        if (orderNumber == null) {
            throw new IllegalArgumentException("orderNumber cannot be null");
        }
        if (orderTimestamp == null) {
            throw new IllegalArgumentException("orderTimestamp cannot be null");
        }
        if (country == null) {
            throw new IllegalArgumentException("country cannot be null");
        }
        if (sku == null) {
            throw new IllegalArgumentException("sku cannot be null");
        }
        if (unitPrice == null) {
            throw new IllegalArgumentException("unitPrice cannot be null");
        }
        if (originalPrice == null) {
            throw new IllegalArgumentException("originalPrice cannot be null");
        }
        if (discountRate == null) {
            throw new IllegalArgumentException("discountRate cannot be null");
        }
        if (discountAmount == null) {
            throw new IllegalArgumentException("discountAmount cannot be null");
        }
        if (subtotalPrice == null) {
            throw new IllegalArgumentException("subtotalPrice cannot be null");
        }
        if (taxRate == null) {
            throw new IllegalArgumentException("taxRate cannot be null");
        }
        if (taxAmount == null) {
            throw new IllegalArgumentException("taxAmount cannot be null");
        }
        if (totalPrice == null) {
            throw new IllegalArgumentException("totalPrice cannot be null");
        }
        if (status == null) {
            throw new IllegalArgumentException("status cannot be null");
        }
        
        this.orderNumber = orderNumber;
        this.orderTimestamp = orderTimestamp;
        this.country = country;
        this.sku = sku;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.originalPrice = originalPrice;
        this.discountRate = discountRate;
        this.discountAmount = discountAmount;
        this.subtotalPrice = subtotalPrice;
        this.taxRate = taxRate;
        this.taxAmount = taxAmount;
        this.totalPrice = totalPrice;
        this.status = status;

    }
}
