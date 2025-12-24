package com.optivem.eshop.systemtest.core.clock.dsl.commands;

import com.optivem.eshop.systemtest.core.clock.driver.ClockDriver;
import com.optivem.eshop.systemtest.core.clock.driver.dtos.error.ClockErrorResponse;
import com.optivem.eshop.systemtest.core.clock.dsl.commands.base.BaseClockCommand;
import com.optivem.eshop.systemtest.core.clock.dsl.commands.base.ClockErrorVerification;
import com.optivem.eshop.systemtest.core.clock.dsl.commands.base.ClockUseCaseResult;
import com.optivem.testing.dsl.UseCaseContext;
import com.optivem.testing.dsl.UseCaseResult;
import com.optivem.testing.dsl.VoidResponseVerification;

public class GoToClock extends BaseClockCommand<Void, VoidResponseVerification<UseCaseContext>> {
    public GoToClock(ClockDriver clockDriver, UseCaseContext useCaseContext) {
        super(clockDriver, useCaseContext);
    }

    @Override
    public ClockUseCaseResult<Void, VoidResponseVerification<UseCaseContext>> execute() {
        var result = driver.goToClock();
        return new ClockUseCaseResult<>(result, context, VoidResponseVerification::new);
    }
}
