package com.godel.simplecrud.service;

import com.godel.simplecrud.dao.jpa.EmployeeJPADao;
import com.godel.simplecrud.dao.jpa.OrderJPADao;
import com.godel.simplecrud.dao.jpa.ProductJPADao;
import com.godel.simplecrud.exceptions.ProductServiceNotFoundException;
import com.godel.simplecrud.model.Employee;
import com.godel.simplecrud.model.Order;
import com.godel.simplecrud.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductJPAServiceImpl implements ProductJPAService {

    private final ProductJPADao productJPADao;
    private final OrderJPADao orderJPADao;
    private final EmployeeJPADao employeeJPADao;

    public ProductJPAServiceImpl(ProductJPADao productJPADao, OrderJPADao orderJPADao, EmployeeJPADao employeeJPADao) {
        this.productJPADao = productJPADao;
        this.orderJPADao = orderJPADao;
        this.employeeJPADao = employeeJPADao;
    }

    @Override
    public List<Product> findAllProducts() {
        return productJPADao.findAll();
    }

    @Override
    public List<Product> findAllProductsByCustomerIdAndOrderId(Long customerId, Long orderId) {
        Employee employee = employeeJPADao.findById(customerId)
                .orElseThrow(() -> new ProductServiceNotFoundException("No such customer with ID=" + customerId));
        Order order = orderJPADao.findOrderByCustomerAndOrderId(employee, orderId)
                .orElseThrow(() -> new ProductServiceNotFoundException("No such order with ID=" + orderId +
                        " for customer with ID=" + customerId));
        log.info("findAllProductsByCustomerIdAndOrderId - got correct request for {}", order);

        return order.getProducts();
    }

    @Override
    public Product findSpecificProductByCustomerIdAndOrderId(Long customerId, Long orderId, Long productId) {
        List<Product> productList = findAllProductsByCustomerIdAndOrderId(customerId, orderId);

        return productList.stream()
                .filter(el -> el.getProductId().equals(productId))
                .findFirst()
                    .orElseThrow(() -> new ProductServiceNotFoundException("No such product with ID=" + productId +
                        " for order with ID=" + orderId));
    }

    @Override
    public Product createProduct(Product product) {
        return productJPADao.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productJPADao.save(product);
    }

    @Override
    public Product findProductById(Long productId) {
        return productJPADao.findById(productId)
                .orElseThrow(() -> new ProductServiceNotFoundException("No such product with ID=" + productId));
    }

}
