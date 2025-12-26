package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;
import com.optivem.eshop.systemtest.core.gherkin.when.WhenClause;

import java.util.ArrayList;
import java.util.List;

public class GivenClause {
    private final SystemDsl app;
    private final ScenarioDsl scenario;
    private final List<ProductBuilder> products;
    private final List<OrderBuilder> orders;
    private final List<OrderBuilder> cancelledOrders;
    private final List<ClockBuilder> clocks;
    private final List<TaxRateBuilder> taxRates;

    public GivenClause(SystemDsl app, ScenarioDsl scenario) {
        this.app = app;
        this.scenario = scenario;
        this.products = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.cancelledOrders = new ArrayList<>();
        this.clocks = new ArrayList<>();
        this.taxRates = new ArrayList<>();
    }

    public ProductBuilder product() {
        var productBuilder = new ProductBuilder(this, app);
        products.add(productBuilder);
        return productBuilder;
    }

    public OrderBuilder order() {
        var orderBuilder = new OrderBuilder(this, app);
        orders.add(orderBuilder);
        return orderBuilder;
    }

    public OrderBuilder cancelledOrder() {
        var orderBuilder = new OrderBuilder(this, app);
        cancelledOrders.add(orderBuilder);
        return orderBuilder;
    }

    public ClockBuilder clock() {
        var clockBuilder = new ClockBuilder(this, app);
        clocks.add(clockBuilder);
        return clockBuilder;
    }

    public TaxRateBuilder taxRate() {
        var taxRateBuilder = new TaxRateBuilder(this, app);
        taxRates.add(taxRateBuilder);
        return taxRateBuilder;
    }

    public EmptyGivenClause noProducts() {
        // No products to create, return clause that allows .when()
        return new EmptyGivenClause(app, scenario);
    }

    public WhenClause when() {
        // Collect all unique countries from orders (including cancelled orders)
        var allOrders = new ArrayList<OrderBuilder>();
        allOrders.addAll(orders);
        allOrders.addAll(cancelledOrders);

        // Execute all clock setups
        // If there are orders but no clock configured, set up a default clock
        if (!allOrders.isEmpty() && clocks.isEmpty()) {
            app.clock().returnsTime()
                    .execute()
                    .shouldSucceed();
        }
        
        for (var clock : clocks) {
            clock.execute(app);
        }

        // Execute all product creations
        for (var product : products) {
            product.execute(app);
        }

        var countriesInOrders = allOrders.stream()
                .map(OrderBuilder::getCountry)
                .distinct()
                .toList();

        // Execute all tax rate setups
        for (var taxRate : taxRates) {
            taxRate.execute(app);
        }

        // Ensure tax rates are set up for all countries used in orders
        var configuredTaxCountries = taxRates.stream()
                .map(TaxRateBuilder::getCountry)
                .toList();

        for (var country : countriesInOrders) {
            if (!configuredTaxCountries.contains(country)) {
                // Set up default tax rate (0.0) for countries without explicit tax configuration
                app.tax().returnsTaxRate()
                        .country(country)
                        .taxRate(0.0)
                        .execute()
                        .shouldSucceed();
            }
        }

        // Execute all order placements
        for (var order : orders) {
            order.execute(app);
        }

        // Execute all cancelled order placements and cancellations
        for (var order : cancelledOrders) {
            order.executeAndCancel(app);
        }

        return new WhenClause(app, scenario);
    }
}
