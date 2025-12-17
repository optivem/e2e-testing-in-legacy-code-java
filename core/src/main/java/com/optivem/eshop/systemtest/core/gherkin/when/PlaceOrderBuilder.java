package com.optivem.eshop.systemtest.core.gherkin.when;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.then.ThenClause;

public class PlaceOrderBuilder {
    private final SystemDsl app;
    private String orderNumber;
    private String sku;
    private String quantityString;
    private Integer quantityInt;

    public PlaceOrderBuilder(SystemDsl app) {
        this.app = app;
    }

    public PlaceOrderBuilder withOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public PlaceOrderBuilder withSku(String sku) {
        this.sku = sku;
        return this;
    }

    public PlaceOrderBuilder withQuantity(int quantity) {
        this.quantityInt = quantity;
        this.quantityString = null;
        return this;
    }

    public PlaceOrderBuilder withQuantity(String quantity) {
        this.quantityString = quantity;
        this.quantityInt = null;
        return this;
    }

    public ThenClause then() {
        // Execute the place order
        var placeOrder = app.shop().placeOrder()
                .orderNumber(orderNumber)
                .sku(sku);
        
        if (quantityString != null) {
            placeOrder.quantity(quantityString);
        } else if (quantityInt != null) {
            placeOrder.quantity(quantityInt);
        }
        
        var result = placeOrder.execute();

        return new ThenClause(app, orderNumber, result);
    }
}
