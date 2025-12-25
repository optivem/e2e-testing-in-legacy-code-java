package com.optivem.eshop.systemtest.smoketests.v6.external;

import com.optivem.eshop.systemtest.base.v5.BaseSystemTest;
import org.junit.jupiter.api.Test;

public class ClockSmokeTest extends BaseSystemTest {

    @Test
    void shouldBeAbleToGoToClock() {
        app.clock().goToClock()
                .execute()
                .shouldSucceed();
    }
}

