package com.godel.simplecrud.service;

import com.godel.simplecrud.dao.jpa.EmployeeJPADao;
import com.godel.simplecrud.dao.jpa.OrderJPADao;
import com.godel.simplecrud.exceptions.OrderServiceNotFoundException;
import com.godel.simplecrud.exceptions.ProductServiceNotFoundException;
import com.godel.simplecrud.model.Employee;
import com.godel.simplecrud.model.Order;
import com.godel.simplecrud.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class OrderJPAServiceImpl implements OrderJPAService {

    private final OrderJPADao orderJPADao;
    private final EmployeeJPADao employeeJPADao;

    public OrderJPAServiceImpl(OrderJPADao orderJPADao, EmployeeJPADao employeeJPADao) {
        this.orderJPADao = orderJPADao;
        this.employeeJPADao = employeeJPADao;
    }

    @Override
    public List<Order> findAllOrders() {
        return orderJPADao.findAll();
    }

    @Override
    public List<Order> findAllOrdersByCustomerId(Long customerId) {
        Employee customer = employeeJPADao.findById(customerId)
                .orElseThrow(() -> new OrderServiceNotFoundException("No such customer with ID=" + customerId));

        return orderJPADao.findAllOrdersByCustomer(customer);
    }

    @Override
    public Order findOrderByCustomerIdAndOrderId(Long customerId, Long orderId) {
        Employee customer = employeeJPADao.findById(customerId)
                .orElseThrow(() -> new OrderServiceNotFoundException("No such customer with ID=" + customerId));

        return orderJPADao.findOrderByCustomerAndOrderId(customer, orderId)
                .orElseThrow(() -> new ProductServiceNotFoundException("No such order with ID=" + orderId +
                " for customer with ID=" + customerId));
    }

    @Override
    public Order findOrderById(Long id) {
        return orderJPADao.findById(id)
                .orElseThrow(() -> new OrderServiceNotFoundException("No such product with ID=" + id));
    }

    @Override
    public Order createOrderForCustomer(Long customerId, Order order) {
        Employee customer = employeeJPADao.findById(customerId)
                .orElseThrow(() -> new OrderServiceNotFoundException("No such customer with ID=" + customerId));

        order.setCustomer(customer);
        order.setTotalPrice(getTotalPriceForOrder(order));

        return orderJPADao.save(order);
    }

    @Override
    public Order updateOrderForCustomer(Long customerId, Order order) {
        Employee customer = employeeJPADao.findById(customerId)
                .orElseThrow(() -> new OrderServiceNotFoundException("No such customer with ID=" + customerId));
        order.setCustomer(customer);
        order.setTotalPrice(getTotalPriceForOrder(order));

        return orderJPADao.save(order);
    }

    private Double getTotalPriceForOrder(Order order) {
        double totalPrice = order.getProducts().stream().
                mapToDouble(Product::getPrice)
                .sum();
        log.info("getTotalPriceForOrder - set total price:{} for order ID={}", totalPrice, order.getOrderId());

        return totalPrice;
    }
}
