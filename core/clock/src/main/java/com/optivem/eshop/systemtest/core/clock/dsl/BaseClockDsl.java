package com.optivem.eshop.systemtest.core.clock.dsl;

import com.optivem.eshop.systemtest.core.clock.driver.ClockDriver;
import com.optivem.eshop.systemtest.core.clock.dsl.commands.GetTime;
import com.optivem.eshop.systemtest.core.clock.dsl.commands.GoToClock;
import com.optivem.eshop.systemtest.core.clock.dsl.commands.ReturnsTime;
import com.optivem.lang.Closer;
import com.optivem.testing.dsl.UseCaseContext;

public class BaseClockDsl implements AutoCloseable {
    protected final ClockDriver driver;
    protected final UseCaseContext context;

    protected BaseClockDsl(ClockDriver driver, UseCaseContext context) {
        this.driver = driver;
        this.context = context;
    }

    @Override
    public void close() {
        Closer.close(driver);
    }

    public GoToClock goToClock() {
        return new GoToClock(driver, context);
    }

    public ReturnsTime returnsTime() {
        return new ReturnsTime(driver, context);
    }

    public GetTime getTime() {
        return new GetTime(driver, context);
    }
}
