package com.godel.simplecrud.service;


import com.godel.simplecrud.model.Product;

import java.util.List;

public interface ProductJPAService {

    List<Product> findAllProducts();

    Product findProductById(Long id);

    Product createProduct(Product product);

    Product updateOrCreateProduct(Product product);

}
