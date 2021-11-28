package com.godel.simplecrud.dao.jpa;

import com.godel.simplecrud.model.Employee;
import com.godel.simplecrud.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderJPADao extends JpaRepository<Order, Long> {

    List<Order> findAllOrdersByCustomer(Employee customer);

    Order findOrderByCustomerAndOrderId(Employee customer, Long orderId);
}