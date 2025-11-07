package com.optivem.eshop.monolith.core.dtos;

import com.optivem.eshop.monolith.core.validation.TypeValidationMessage;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PlaceOrderRequest {
    @TypeValidationMessage("Product ID must be an integer")
    private long productId;

    @TypeValidationMessage("Quantity must be an integer")
    @Positive(message = "Quantity must be positive")
    private int quantity;
}