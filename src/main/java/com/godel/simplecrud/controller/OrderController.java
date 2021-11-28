package com.godel.simplecrud.controller;

import com.godel.simplecrud.model.Order;
import com.godel.simplecrud.service.OrderJPAService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

    private final OrderJPAService orderJPAService;

    public OrderController(OrderJPAService orderJPAService) {
        this.orderJPAService = orderJPAService;
    }

    @GetMapping("/orders")
    public List<Order> showAllOrders(HttpServletRequest request) {
        log.info("IN: showAllOrders - Request: [method:{}] URI: {}",
                request.getMethod(), request.getRequestURI());

        List<Order> allOrders = orderJPAService.findAllOrders();

        log.info("OUT: showAllOrders - found {} orders", allOrders.size());
        return allOrders;
    }

    @GetMapping("/employees/{customerId}/orders")
    public List<Order> showOrdersByCustomer(@PathVariable Long customerId, HttpServletRequest request) {
        log.info("IN: showOrdersByCustomer - Request: [method:{}] URI: {}",
                request.getMethod(), request.getRequestURI());

        List<Order> ordersByCustomer = orderJPAService.findAllOrdersByCustomerId(customerId);

        log.info("OUT: showOrdersByCustomer - found {} orders for customer with ID={}", ordersByCustomer.size(), customerId);
        return ordersByCustomer;
    }

    @GetMapping("/employees/{customerId}/orders/{orderId}")
    public Order showOrderById(@PathVariable Long customerId,
                               @PathVariable Long orderId, HttpServletRequest request) {
        log.info("IN: showOrderById - Request: [method:{}] URI: {}",
                request.getMethod(), request.getRequestURI());

        Order order = orderJPAService.findOrderByCustomerIdAndOrderId(customerId, orderId);

        log.info("OUT: showOrderById - found {}", order);
        return order;
    }

    @PostMapping("/employees/{customerId}/orders")
    public Order createOrderForCustomer(@PathVariable Long customerId,
                                        @Valid @RequestBody Order order, HttpServletRequest request) {
        log.info("IN: createOrderForCustomer - Request: [method:{}] URI: {}",
                request.getMethod(), request.getRequestURI());

        Order createdOrder = orderJPAService.createOrderForCustomer(customerId, order);

        log.info("OUT: createOrderForCustomer - created {}", createdOrder);
        return createdOrder;
    }

    @PutMapping("/employees/{customerId}/orders/{orderId}")
    public Order updateOrderForCustomer(@PathVariable Long customerId,
                                        @PathVariable Long orderId,
                                        @Valid @RequestBody Order order,
                                        HttpServletRequest request) {
        log.info("IN: updateOrderForCustomer - Request: [method:{}] URI: {}",
                request.getMethod(), request.getRequestURI());

        order.setOrderId(orderId);
        Order updatedOrder = orderJPAService.updateOrderForCustomer(customerId, order);

        log.info("OUT: updateOrderForCustomer - updated {}", order);
        return updatedOrder;
    }
}
