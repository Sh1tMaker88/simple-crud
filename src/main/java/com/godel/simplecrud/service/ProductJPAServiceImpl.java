package com.godel.simplecrud.service;

import com.godel.simplecrud.dao.jpa.ProductJPADao;
import com.godel.simplecrud.exceptions.ProductServiceNotFoundException;
import com.godel.simplecrud.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductJPAServiceImpl implements ProductService {

    private ProductJPADao productJPADao;

    public ProductJPAServiceImpl(ProductJPADao productJPADao) {
        this.productJPADao = productJPADao;
    }

    @Override
    public List<Product> findAllProducts() {
        return productJPADao.findAll();
    }

    @Override
    public Product findProductById(Long id) {
        Optional<Product> productOptional = productJPADao.findById(id);

        if (productOptional.isEmpty()) {
            throw new ProductServiceNotFoundException("No such product with ID=" + id);
        }

        return productOptional.get();
    }

    @Override
    public Product createProduct(Product product) {
        return productJPADao.save(product);
    }

    @Override
    public Product updateOrCreateProduct(Product product) {
        return productJPADao.save(product);
    }
}
