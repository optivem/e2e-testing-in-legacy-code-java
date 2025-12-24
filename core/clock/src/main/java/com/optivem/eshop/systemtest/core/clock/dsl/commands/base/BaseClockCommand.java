package com.optivem.eshop.systemtest.core.clock.dsl.commands.base;

import com.optivem.eshop.systemtest.core.clock.driver.ClockDriver;
import com.optivem.eshop.systemtest.core.clock.driver.dtos.error.ClockErrorResponse;
import com.optivem.testing.dsl.BaseUseCase;
import com.optivem.testing.dsl.UseCaseContext;
import com.optivem.testing.dsl.UseCaseResult;

public abstract class BaseClockCommand<TResponse, TVerification> extends BaseUseCase<ClockDriver, UseCaseContext, TResponse, ClockErrorResponse, TVerification, ClockErrorVerification> {
    protected BaseClockCommand(ClockDriver clockDriver, UseCaseContext useCaseContext) {
        super(clockDriver, useCaseContext);
    }
}
