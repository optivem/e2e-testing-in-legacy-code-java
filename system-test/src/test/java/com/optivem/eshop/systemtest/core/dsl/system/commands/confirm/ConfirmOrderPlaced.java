package com.optivem.eshop.systemtest.core.dsl.system.commands.confirm;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.drivers.system.commons.dtos.PlaceOrderResponse;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;
import com.optivem.eshop.systemtest.core.dsl.system.commands.BaseShopCommand;

import static com.optivem.testing.assertions.ResultAssert.assertThatResult;

public class ConfirmOrderPlaced extends BaseShopCommand {
    private String orderNumberResultAlias;

    protected ConfirmOrderPlaced(ShopDriver driver, DslContext context) {
        super(driver, context);
    }

    public ConfirmOrderPlaced orderNumber(String orderNumberResultAlias) {
        this.orderNumberResultAlias = orderNumberResultAlias;
        return this;
    }

    @Override
    public void execute() {
        var result = context.results().getResult("placeOrder", orderNumberResultAlias, PlaceOrderResponse.class);
        assertThatResult(result).isSuccess();
    }
}
