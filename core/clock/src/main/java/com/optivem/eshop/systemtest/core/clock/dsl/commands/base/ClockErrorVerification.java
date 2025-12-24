package com.optivem.eshop.systemtest.core.clock.dsl.commands.base;

import com.optivem.eshop.systemtest.core.clock.driver.dtos.error.ClockErrorResponse;
import com.optivem.testing.dsl.ResponseVerification;
import com.optivem.testing.dsl.UseCaseContext;

public class ClockErrorVerification extends ResponseVerification<ClockErrorResponse, UseCaseContext> {
    public ClockErrorVerification(ClockErrorResponse clockErrorResponse, UseCaseContext useCaseContext) {
        super(clockErrorResponse, useCaseContext);
    }
}
