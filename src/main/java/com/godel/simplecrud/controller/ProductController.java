package com.godel.simplecrud.controller;

import com.godel.simplecrud.model.Product;
import com.godel.simplecrud.service.ProductJPAServiceImpl;
import com.godel.simplecrud.service.ProductJPAService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
public class ProductController {

    private final ProductJPAService productJPAService;

    public ProductController(ProductJPAServiceImpl productJPAService) {
        this.productJPAService = productJPAService;
    }

    @GetMapping("/products")

    private List<Product> showAllProducts(HttpServletRequest request) {

        log.info("IN: showAllProducts - Request: [method:{}] URI: {}",
                request.getMethod(), request.getRequestURI());

        List<Product> allProducts = productJPAService.findAllProducts();

        log.info("OUT: showAllProducts - found {} products", allProducts.size());
        return allProducts;
    }
}
