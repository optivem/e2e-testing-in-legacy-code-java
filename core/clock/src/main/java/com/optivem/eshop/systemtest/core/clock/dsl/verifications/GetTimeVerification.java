package com.optivem.eshop.systemtest.core.clock.dsl.verifications;

import com.optivem.eshop.systemtest.core.clock.driver.dtos.GetTimeResponse;
import com.optivem.testing.dsl.ResponseVerification;
import com.optivem.testing.dsl.UseCaseContext;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class GetTimeVerification extends ResponseVerification<GetTimeResponse, UseCaseContext> {

    public GetTimeVerification(GetTimeResponse response, UseCaseContext context) {
        super(response, context);
    }

    public GetTimeVerification timeIsNotNull() {
        assertThat(response.getTime())
                .withFailMessage("Expected time to be not null")
                .isNotNull();
        return this;
    }

    public GetTimeVerification timeIsAfter(Instant instant) {
        assertThat(response.getTime())
                .withFailMessage("Expected time %s to be after %s", response.getTime(), instant)
                .isAfter(instant);
        return this;
    }

    public GetTimeVerification timeIsBefore(Instant instant) {
        assertThat(response.getTime())
                .withFailMessage("Expected time %s to be before %s", response.getTime(), instant)
                .isBefore(instant);
        return this;
    }

    public GetTimeVerification timeIsBetween(Instant start, Instant end) {
        assertThat(response.getTime())
                .withFailMessage("Expected time %s to be between %s and %s", response.getTime(), start, end)
                .isBetween(start, end);
        return this;
    }
}

