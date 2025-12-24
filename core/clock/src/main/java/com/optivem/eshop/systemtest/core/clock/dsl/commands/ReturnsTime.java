package com.optivem.eshop.systemtest.core.clock.dsl.commands;

import com.optivem.eshop.systemtest.core.clock.driver.ClockDriver;
import com.optivem.eshop.systemtest.core.clock.driver.ClockStubDriver;
import com.optivem.eshop.systemtest.core.clock.driver.dtos.ReturnsTimeRequest;
import com.optivem.eshop.systemtest.core.clock.dsl.commands.base.BaseClockCommand;
import com.optivem.eshop.systemtest.core.clock.dsl.commands.base.ClockUseCaseResult;
import com.optivem.testing.dsl.UseCaseContext;
import com.optivem.testing.dsl.VoidResponseVerification;

import java.time.Instant;

public class ReturnsTime extends BaseClockCommand<Void, VoidResponseVerification<UseCaseContext>> {

    private Instant time;

    public ReturnsTime(ClockDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    public ReturnsTime time(Instant time) {
        this.time = time;
        return this;
    }

    @Override
    public ClockUseCaseResult<Void, VoidResponseVerification<UseCaseContext>> execute() {

        var request = ReturnsTimeRequest.builder()
            .time(time)
            .build();

        var result = driver.returnsTime(request);

        return new ClockUseCaseResult<>(
            result,
            context,
            VoidResponseVerification::new
        );
    }
}
