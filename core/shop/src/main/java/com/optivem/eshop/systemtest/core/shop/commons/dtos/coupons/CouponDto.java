package com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponDto {
    private String code;
    private double discountRate;
    private Instant validFrom;
    private Instant validTo;    
    private Integer usageLimit;    private Integer usedCount;}
