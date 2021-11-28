package com.godel.simplecrud.service;

import com.godel.simplecrud.model.Order;

import java.util.List;

public interface OrderJPAService {

    List<Order> findAllOrders();

    List<Order> findAllOrdersByCustomerId(Long customerId);

    Order findOrderByCustomerIdAndOrderId(Long customerId, Long orderId);

    Order findOrderById(Long id);

    Order createOrder(Order order);

    Order createOrderForCustomer(Long customerId, Order order);

    Order updateOrCreateOrder(Order order);

    Order updateOrderForCustomer(Long customerId, Order order);
}
