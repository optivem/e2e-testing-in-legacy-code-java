package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;
import com.optivem.eshop.systemtest.core.gherkin.when.WhenClause;

import java.util.ArrayList;
import java.util.List;

public class GivenClause {
    private final SystemDsl app;
    private final ScenarioDsl scenario;
    private final List<GivenProductBuilder> products;
    private final List<GivenOrderBuilder> orders;
    private GivenClockBuilder clock;
    private final List<GivenTaxRateBuilder> taxRates;
    private final List<GivenCouponBuilder> coupons;

    public GivenClause(SystemDsl app, ScenarioDsl scenario) {
        this.app = app;
        this.scenario = scenario;
        this.products = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.clock = new GivenClockBuilder(this);
        this.taxRates = new ArrayList<>();
        this.coupons = new ArrayList<>();
    }

    public GivenProductBuilder product() {
        var productBuilder = new GivenProductBuilder(this);
        products.add(productBuilder);
        return productBuilder;
    }

    public GivenOrderBuilder order() {
        var orderBuilder = new GivenOrderBuilder(this);
        orders.add(orderBuilder);
        return orderBuilder;
    }

    public GivenClockBuilder clock() {
        clock = new GivenClockBuilder(this);
        return clock;
    }

    public GivenTaxRateBuilder taxRate() {
        var taxRateBuilder = new GivenTaxRateBuilder(this);
        taxRates.add(taxRateBuilder);
        return taxRateBuilder;
    }

    public GivenCouponBuilder coupon() {
        var couponBuilder = new GivenCouponBuilder(this);
        coupons.add(couponBuilder);
        return couponBuilder;
    }

    public WhenClause when() {
        long start = System.currentTimeMillis();
        System.out.println("[PERF] GivenClause.when() starting setup...");
        
        long cStart = System.currentTimeMillis();
        setupClock();
        System.out.println("[PERF] setupClock took " + (System.currentTimeMillis() - cStart) + "ms");
        
        long eStart = System.currentTimeMillis();
        setupErp();
        System.out.println("[PERF] setupErp took " + (System.currentTimeMillis() - eStart) + "ms");
        
        long tStart = System.currentTimeMillis();
        setupTax();
        System.out.println("[PERF] setupTax took " + (System.currentTimeMillis() - tStart) + "ms");
        
        long sStart = System.currentTimeMillis();
        setupShop();
        System.out.println("[PERF] setupShop took " + (System.currentTimeMillis() - sStart) + "ms");

        long elapsed = System.currentTimeMillis() - start;
        System.out.println("[PERF] GivenClause.when() total setup took " + elapsed + "ms");
        
        return new WhenClause(app, scenario, !products.isEmpty(), !taxRates.isEmpty());
    }

    private void setupClock() {
        clock.execute(app);
    }

    private void setupErp() {
        if (!orders.isEmpty() && products.isEmpty()) {
            var defaultProduct = new GivenProductBuilder(this);
            products.add(defaultProduct);
        }

        for (var product : products) {
            product.execute(app);
        }
    }

    private void setupTax() {
        if (!orders.isEmpty() && taxRates.isEmpty()) {
            var defaultTaxRate = new GivenTaxRateBuilder(this);
            taxRates.add(defaultTaxRate);
        }

        for (var taxRate : taxRates) {
            taxRate.execute(app);
        }
    }

    private void setupShop() {
        setupCoupons();
        setupOrders();
    }

    private void setupCoupons() {
        if(!orders.isEmpty() && coupons.isEmpty()) {
            var defaultCoupon = new GivenCouponBuilder(this);
            coupons.add(defaultCoupon);
        }

        for (var coupon : coupons) {
            coupon.execute(app);
        }
    }

    private void setupOrders() {
        for (var order : orders) {
            order.execute(app);
        }
    }
}
