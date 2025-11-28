package com.optivem.eshop.systemtest.e2etests;

import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.optivem.eshop.systemtest.core.commons.assertions.ResultAssert.assertThatResult;

class ApiE2eTest extends BaseE2eTest {

    @Override
    protected ShopDriver createDriver() {
        return DriverFactory.createShopApiDriver();
    }

    @Test
    void shouldRejectOrderWithNullQuantity() {
        var result = shopDriver.placeOrder("some-sku", null, "US");
        assertThatResult(result).isFailureWithError("Quantity must not be empty");
    }

    @Test
    void shouldRejectOrderWithNullSku() {
        var result = shopDriver.placeOrder(null, "5", "US");
        assertThatResult(result).isFailureWithError("SKU must not be empty");
    }

    @Test
    void shouldRejectOrderWithNullCountry() {
        var result = shopDriver.placeOrder("some-sku", "5", null);
        assertThatResult(result).isFailureWithError("Country must not be empty");
    }

    @Test
    void shouldNotCancelNonExistentOrder() {
        var result = shopDriver.cancelOrder("NON-EXISTENT-ORDER-99999");
        assertThatResult(result).isFailureWithError("Order NON-EXISTENT-ORDER-99999 does not exist.");
    }

    @Test
    void shouldNotCancelAlreadyCancelledOrder() {
        var sku = "MNO-" + UUID.randomUUID();
        erpApiDriver.createProduct(sku, "35.00");
        var placeOrderResult = shopDriver.placeOrder(sku, "3", "US");
        assertThatResult(placeOrderResult).isSuccess();

        var orderNumber = placeOrderResult.getValue().getOrderNumber();

        // Cancel the order first time - should succeed
        var firstCancelResult = shopDriver.cancelOrder(orderNumber);
        assertThatResult(firstCancelResult).isSuccess();

        // Try to cancel the same order again - should fail
        var secondCancelResult = shopDriver.cancelOrder(orderNumber);
        assertThatResult(secondCancelResult).isFailureWithError("Order has already been cancelled");
    }
}

