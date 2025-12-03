package com.optivem.eshop.systemtest.smoketests;

import com.optivem.eshop.systemtest.core.channels.library.Channel;
import com.optivem.eshop.systemtest.core.channels.library.ChannelExtension;
import com.optivem.eshop.systemtest.core.channels.ChannelType;
import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.drivers.commons.clients.Closer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.optivem.eshop.systemtest.core.drivers.commons.ResultAssert.assertThatResult;

@ExtendWith(ChannelExtension.class)
public class ShopSmokeTest {

    private ShopDriver shopDriver;

    @BeforeEach
    void setUp() {
        shopDriver = DriverFactory.createShopDriver();
    }

    @AfterEach
    void tearDown() {
        Closer.close(shopDriver);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldBeAbleToGoToShop() {
        var result = shopDriver.goToShop();
        assertThatResult(result).isSuccess();
    }
}
