package com.optivem.eshop.systemtest.core.clock.driver.dtos.error;

import com.optivem.eshop.systemtest.core.clock.client.dtos.error.ExtClockErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClockErrorResponse {
    private String message;

    public static ClockErrorResponse from(ExtClockErrorResponse errorResponse) {
        return ClockErrorResponse.builder().message(errorResponse.getMessage()).build();
    }
}
