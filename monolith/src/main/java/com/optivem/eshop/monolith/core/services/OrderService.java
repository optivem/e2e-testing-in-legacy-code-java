package com.optivem.eshop.monolith.core.services;

import com.optivem.eshop.monolith.core.entities.Order;
import com.optivem.eshop.monolith.core.entities.OrderStatus;
import com.optivem.eshop.monolith.core.exceptions.NotExistValidationException;
import com.optivem.eshop.monolith.core.exceptions.ValidationException;
import com.optivem.eshop.monolith.core.repositories.OrderRepository;
import com.optivem.eshop.monolith.core.services.external.ErpGateway;
import com.optivem.eshop.monolith.core.dtos.GetOrderResponse;
import com.optivem.eshop.monolith.core.dtos.PlaceOrderRequest;
import com.optivem.eshop.monolith.core.dtos.PlaceOrderResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;

@Service
public class OrderService {

    public static final MonthDay DECEMBER_31 = MonthDay.of(12, 31);
    private static final LocalTime CANCELLATION_BLOCK_START = LocalTime.of(22, 0);
    private static final LocalTime CANCELLATION_BLOCK_END = LocalTime.of(23, 0);

    private final OrderRepository orderRepository;
    private final ErpGateway erpGateway;

    public OrderService(OrderRepository orderRepository, ErpGateway erpGateway) {
        this.orderRepository = orderRepository;
        this.erpGateway = erpGateway;
    }

    public PlaceOrderResponse placeOrder(PlaceOrderRequest request) {
        var sku = request.getSku();
        var quantity = request.getQuantity();
        var country = request.getCountry();

        var productDetails = erpGateway.getProductDetails(sku);
        if (productDetails.isEmpty()) {
            throw new ValidationException("Product does not exist for SKU: " + sku);
        }

        var orderNumber = orderRepository.nextOrderNumber();
        var unitPrice = productDetails.get().getPrice();
        var totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
        var order = new Order(orderNumber, sku, quantity, unitPrice, totalPrice, OrderStatus.PLACED, country);

        orderRepository.addOrder(order);

        var response = new PlaceOrderResponse();
        response.setOrderNumber(orderNumber);
        return response;
    }

    public GetOrderResponse getOrder(String orderNumber) {
        var optionalOrder = orderRepository.getOrder(orderNumber);

        if(optionalOrder.isEmpty()) {
            throw new NotExistValidationException("Order " + orderNumber + " does not exist.");
        }

        var order = optionalOrder.get();

        var response = new GetOrderResponse();
        response.setOrderNumber(orderNumber);
        response.setSku(order.getSku());
        response.setQuantity(order.getQuantity());
        response.setUnitPrice(order.getUnitPrice());
        response.setTotalPrice(order.getTotalPrice());
        response.setStatus(order.getStatus());
        response.setCountry(order.getCountry());

        return response;
    }

    public void cancelOrder(String orderNumber) {
        var optionalOrder = orderRepository.getOrder(orderNumber);

        if(optionalOrder.isEmpty()) {
            throw new NotExistValidationException("Order " + orderNumber + " does not exist.");
        }

        var order = optionalOrder.get();

        var now = LocalDateTime.now();
        var currentDate = MonthDay.from(now);
        var currentTime = now.toLocalTime();

        if (currentDate.equals(DECEMBER_31) &&
            currentTime.isAfter(CANCELLATION_BLOCK_START) && 
            currentTime.isBefore(CANCELLATION_BLOCK_END)) {
            throw new ValidationException("Order cancellation is not allowed on December 31st between 22:00 and 23:00");
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.updateOrder(order);
    }
}
