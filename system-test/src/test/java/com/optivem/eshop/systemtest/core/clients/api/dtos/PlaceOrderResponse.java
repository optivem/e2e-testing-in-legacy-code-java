package com.optivem.eshop.systemtest.core.clients.api.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class PlaceOrderResponse {
    private String orderNumber;
}

