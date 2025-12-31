package com.optivem.eshop.systemtest.core.gherkin.when;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.*;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;
import com.optivem.eshop.systemtest.core.gherkin.then.ThenClause;

public class PlaceOrderBuilder {
    private final SystemDsl app;
    private final ScenarioDsl scenario;

    private String orderNumber;
    private String sku;
    private String quantity;
    private String country;
    private String couponCodeAlias;

    public PlaceOrderBuilder(SystemDsl app, ScenarioDsl scenario) {
        this.app = app;
        this.scenario = scenario;
        
        withOrderNumber(DEFAULT_ORDER_NUMBER);
        withSku(DEFAULT_SKU);
        withQuantity(DEFAULT_QUANTITY);
        withCountry(DEFAULT_COUNTRY);
        withCouponCode(EMPTY);
    }

    public PlaceOrderBuilder withOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public PlaceOrderBuilder withSku(String sku) {
        this.sku = sku;
        return this;
    }

    public PlaceOrderBuilder withQuantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public PlaceOrderBuilder withQuantity(int quantity) {
        return withQuantity(String.valueOf(quantity));
    }

    public PlaceOrderBuilder withCountry(String country) {
        this.country = country;
        return this;
    }

    public PlaceOrderBuilder withCouponCode(String couponCodeAlias) {
        this.couponCodeAlias = couponCodeAlias;
        return this;
    }

    public ThenClause then() {
        var result = app.shop().placeOrder()
                .orderNumber(orderNumber)
                .sku(sku)
                .quantity(quantity)
                .country(country)
                .couponCode(couponCodeAlias)
                .execute();

        return new ThenClause(app, scenario, orderNumber, result);
    }
}
