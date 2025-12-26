package com.optivem.eshop.systemtest.acceptancetests.v7;

import com.optivem.eshop.systemtest.acceptancetests.v7.base.BaseAcceptanceTest;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.core.shop.client.dtos.enums.OrderStatus;
import com.optivem.testing.channels.Channel;
import org.junit.jupiter.api.TestTemplate;

import static com.optivem.eshop.systemtest.acceptancetests.commons.constants.Defaults.ORDER_NUMBER;
import static com.optivem.eshop.systemtest.acceptancetests.commons.constants.Defaults.SKU;

public class CancelOrderPositiveTest extends BaseAcceptanceTest {

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldCancelOrder() {
        scenario
                .given().order()
                .when().cancelOrder()
                .then().shouldSucceed()
                .and().order().hasStatus(OrderStatus.CANCELLED);
    }
}

