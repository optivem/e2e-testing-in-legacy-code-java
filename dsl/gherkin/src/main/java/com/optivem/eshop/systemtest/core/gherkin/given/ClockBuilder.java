package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.when.WhenClause;

import java.time.Instant;

public class ClockBuilder {
    private final GivenClause givenClause;
    private Instant time;

    public ClockBuilder(GivenClause givenClause) {
        this.givenClause = givenClause;
    }

    public ClockBuilder withTime(Instant time) {
        this.time = time;
        return this;
    }

    public GivenClause and() {
        return givenClause;
    }

    public WhenClause when() {
        return givenClause.when();
    }

    void execute(SystemDsl app) {
        var clockSetup = app.clock().returnsTime();
        if (this.time != null) {
            clockSetup.time(this.time);
        }
        clockSetup.execute().shouldSucceed();
    }
}

