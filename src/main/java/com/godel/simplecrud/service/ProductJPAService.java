package com.godel.simplecrud.service;


import com.godel.simplecrud.model.Product;

import java.util.List;

public interface ProductJPAService {

    List<Product> findAllProducts();

    List<Product> findAllProductsByCustomerIdAndOrderId(Long customerId, Long orderId);

    Product findSpecificProductByCustomerIdAndOrderId(Long customerId, Long orderId, Long productId);

    Product createProduct(Product product);

    Product updateProduct(Product product);

    Product findProductById(Long productId);
}
