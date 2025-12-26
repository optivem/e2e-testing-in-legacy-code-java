package com.optivem.eshop.systemtest.core.clock.dsl.commands;

import com.optivem.eshop.systemtest.core.clock.driver.ClockDriver;
import com.optivem.eshop.systemtest.core.clock.driver.dtos.ReturnsTimeRequest;
import com.optivem.eshop.systemtest.core.clock.dsl.commands.base.BaseClockCommand;
import com.optivem.eshop.systemtest.core.clock.dsl.commands.base.ClockUseCaseResult;
import com.optivem.testing.dsl.UseCaseContext;
import com.optivem.testing.dsl.VoidVerification;

import java.time.Instant;

public class ReturnsTime extends BaseClockCommand<Void, VoidVerification<UseCaseContext>> {

    private static final Instant DEFAULT_TIME = Instant.parse("2025-12-24T10:00:00Z");

    private Instant time;

    public ReturnsTime(ClockDriver driver, UseCaseContext context) {
        super(driver, context);
        time(DEFAULT_TIME);
    }

    public ReturnsTime time(Instant time) {
        this.time = time;
        return this;
    }

    public ReturnsTime time(String time) {
        return time(Instant.parse(time));
    }

    @Override
    public ClockUseCaseResult<Void, VoidVerification<UseCaseContext>> execute() {

        var request = ReturnsTimeRequest.builder()
            .time(time)
            .build();

        var result = driver.returnsTime(request);

        return new ClockUseCaseResult<>(
            result,
            context,
            VoidVerification::new
        );
    }
}
