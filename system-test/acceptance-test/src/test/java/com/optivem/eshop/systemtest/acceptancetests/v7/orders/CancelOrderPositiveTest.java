package com.optivem.eshop.systemtest.acceptancetests.v7.orders;

import com.optivem.eshop.systemtest.acceptancetests.v7.base.BaseAcceptanceTest;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.OrderStatus;
import com.optivem.testing.annotations.Time;
import com.optivem.testing.channels.Channel;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;

import java.time.Instant;

public class CancelOrderPositiveTest extends BaseAcceptanceTest {

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldBeAbleToCancelAnyPlacedOrder() {
        System.out.println("[" + Instant.now() + "] START shouldBeAbleToCancelAnyPlacedOrder - Thread: " + Thread.currentThread().getName());
        scenario
                .given().order().withStatus(OrderStatus.PLACED)
                .when().cancelOrder()
                .then().shouldSucceed();
        System.out.println("[" + Instant.now() + "] END shouldBeAbleToCancelAnyPlacedOrder - Thread: " + Thread.currentThread().getName());
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldHaveCancelledStatusWhenCancelled() {
        System.out.println("[" + Instant.now() + "] START shouldHaveCancelledStatusWhenCancelled - Thread: " + Thread.currentThread().getName());
        scenario
                .given().order()
                .when().cancelOrder()
                .then().order().hasStatus(OrderStatus.CANCELLED);
        System.out.println("[" + Instant.now() + "] END shouldHaveCancelledStatusWhenCancelled - Thread: " + Thread.currentThread().getName());
    }

    // @Disabled("TODO: VJ: Passes when I run it alone, but fails when run with class")
    @Time
    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldBeAbleToCancelAnOrderOn31stDecBetween2200And2230() {
        System.out.println("[" + Instant.now() + "] START shouldBeAbleToCancelAnOrderOn31stDecBetween2200And2230 - Thread: " + Thread.currentThread().getName());
        scenario
                .given().clock().withTime("2024-12-31T22:15:00Z")
                .and().order().withStatus(OrderStatus.PLACED)
                .when().cancelOrder()
                .then().shouldFail().errorMessage("Order cancellation is not allowed on December 31st between 22:00 and 23:00")
                .and().order().hasStatus(OrderStatus.PLACED);
        System.out.println("[" + Instant.now() + "] END shouldBeAbleToCancelAnOrderOn31stDecBetween2200And2230 - Thread: " + Thread.currentThread().getName());
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void test4() {
        System.out.println("[" + Instant.now() + "] START test4 - Thread: " + Thread.currentThread().getName());
        scenario
                .given().order().withStatus(OrderStatus.PLACED)
                .when().cancelOrder()
                .then().shouldSucceed();
        System.out.println("[" + Instant.now() + "] END test4 - Thread: " + Thread.currentThread().getName());
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void test5() {
        System.out.println("[" + Instant.now() + "] START test5 - Thread: " + Thread.currentThread().getName());
        scenario
                .given().order().withStatus(OrderStatus.PLACED)
                .when().cancelOrder()
                .then().shouldSucceed();
        System.out.println("[" + Instant.now() + "] END test5 - Thread: " + Thread.currentThread().getName());
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void test6() {
        System.out.println("[" + Instant.now() + "] START test6 - Thread: " + Thread.currentThread().getName());
        scenario
                .given().order().withStatus(OrderStatus.PLACED)
                .when().cancelOrder()
                .then().shouldSucceed();
        System.out.println("[" + Instant.now() + "] END test6 - Thread: " + Thread.currentThread().getName());
    }

}

