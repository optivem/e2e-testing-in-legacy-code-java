package com.optivem.eshop.systemtest.core.clock.dsl;

import com.optivem.eshop.systemtest.core.clock.driver.ClockStubDriver;
import com.optivem.testing.dsl.UseCaseContext;

public class ClockStubDsl extends BaseClockDsl {

    public ClockStubDsl(String baseUrl, UseCaseContext context) {
        super(new ClockStubDriver(baseUrl), context);
    }
}
