package com.optivem.eshop.systemtest.acceptancetests;

import com.optivem.eshop.systemtest.BaseSystemTest;
import com.optivem.eshop.systemtest.Environment;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.testing.channels.Channel;
import com.optivem.testing.dsl.ExternalSystemMode;
import org.junit.jupiter.api.TestTemplate;

import java.time.Instant;

public class AcceptanceTest extends BaseSystemTest {

    @Override
    protected Environment getFixedEnvironment() {
        return Environment.LOCAL;
    }

    @Override
    protected ExternalSystemMode getFixedExternalSystemMode() {
        return ExternalSystemMode.STUB;
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldPlaceOrderWithCorrectOriginalPrice() {
        app.clock().returnsTime()
                        .time(Instant.parse("2025-12-24T10:00:00Z"))
                                .execute()
                                        .shouldSucceed();


        app.erp().returnsProduct()
                .sku("SKU-123")
                .unitPrice(20.00)
                .execute()
                .shouldSucceed();

        app.tax().returnsTaxRate()
                        .country("AR")
                                .taxRate(0.05)
                .execute()
                .shouldSucceed();

        app.shop().placeOrder().orderNumber("ORDER-1001")
                .sku("SKU-123")
                .quantity(5)
                .country("AR")
                .execute()
                .shouldSucceed();

        app.shop().viewOrder().orderNumber("ORDER-1001").execute()
                .shouldSucceed()
                .originalPrice(100.00)
                .discountAmount(0.15);
    }
}
