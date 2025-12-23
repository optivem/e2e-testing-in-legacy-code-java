package com.optivem.eshop.systemtest.core.erp.driver.dtos.error;

import com.optivem.eshop.systemtest.core.erp.client.dtos.error.ExtErpErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErpErrorResponse {
    private String message;

    public static ErpErrorResponse from(ExtErpErrorResponse errorResponse) {
        return ErpErrorResponse.builder().message(errorResponse.getMessage()).build();
    }
}

