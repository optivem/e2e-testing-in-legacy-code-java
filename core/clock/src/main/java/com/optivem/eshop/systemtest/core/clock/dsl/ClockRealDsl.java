package com.optivem.eshop.systemtest.core.clock.dsl;

import com.optivem.eshop.systemtest.core.clock.driver.ClockRealDriver;
import com.optivem.testing.dsl.UseCaseContext;

public class ClockRealDsl extends BaseClockDsl {

    public ClockRealDsl(UseCaseContext context) {
        super(new ClockRealDriver(), context);
    }
}
