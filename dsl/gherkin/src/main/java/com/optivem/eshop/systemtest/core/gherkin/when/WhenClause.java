package com.optivem.eshop.systemtest.core.gherkin.when;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.*;

public class WhenClause {
    private final SystemDsl app;
    private final ScenarioDsl scenario;
    private boolean hasProduct;
    private boolean hasTaxRate;

    public WhenClause(SystemDsl app, ScenarioDsl scenario) {
        this(app, scenario, false, false);
    }

    public WhenClause(SystemDsl app, ScenarioDsl scenario, boolean hasProduct, boolean hasTaxRate) {
        this.app = app;
        this.scenario = scenario;
        this.hasProduct = hasProduct;
        this.hasTaxRate = hasTaxRate;
    }

    private void ensureDefaults() {
        long start = System.currentTimeMillis();
        if (!hasProduct) {
            long pStart = System.currentTimeMillis();
            app.erp().returnsProduct()
                    .sku(DEFAULT_SKU)
                    .unitPrice(DEFAULT_UNIT_PRICE)
                    .execute()
                    .shouldSucceed();
            hasProduct = true;
            System.out.println("[PERF] ensureDefaults - default product took " + (System.currentTimeMillis() - pStart) + "ms");
        }

        if (!hasTaxRate) {
            long tStart = System.currentTimeMillis();
            app.tax().returnsTaxRate()
                    .country(DEFAULT_COUNTRY)
                    .taxRate(DEFAULT_TAX_RATE)
                    .execute()
                    .shouldSucceed();
            hasTaxRate = true;
            System.out.println("[PERF] ensureDefaults - default taxRate took " + (System.currentTimeMillis() - tStart) + "ms");
        }
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("[PERF] WhenClause.ensureDefaults total took " + elapsed + "ms");
    }

    public PlaceOrderBuilder placeOrder() {
        ensureDefaults();
        return new PlaceOrderBuilder(app, scenario);
    }

    public CancelOrderBuilder cancelOrder() {
        ensureDefaults();
        return new CancelOrderBuilder(app, scenario);
    }

    public ViewOrderBuilder viewOrder() {
        ensureDefaults();
        return new ViewOrderBuilder(app, scenario);
    }

    public PublishCouponBuilder publishCoupon() {
        return new PublishCouponBuilder(app, scenario);
    }

    public BrowseCouponsBuilder browseCoupons() {
        return new BrowseCouponsBuilder(app, scenario);
    }
}
