package com.godel.simplecrud.dao.jpa;

import com.godel.simplecrud.model.Order;
import com.godel.simplecrud.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductJPADao extends JpaRepository<Product, Long> {
}