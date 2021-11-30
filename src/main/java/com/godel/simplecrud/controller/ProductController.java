package com.godel.simplecrud.controller;

import com.godel.simplecrud.exceptions.ErrorMessage;
import com.godel.simplecrud.model.Product;
import com.godel.simplecrud.service.ProductJPAServiceImpl;
import com.godel.simplecrud.service.ProductJPAService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Slf4j
@Tag(name = "Product controller")
@Validated
public class ProductController {

    private final ProductJPAService productJPAService;

    public ProductController(ProductJPAServiceImpl productJPAService) {
        this.productJPAService = productJPAService;
    }

    @GetMapping("/products")
    @Operation(summary = "Show all products")
    @ApiResponse(responseCode = "200", description = "Found products", content = {
            @Content(mediaType = "application/json", array =
            @ArraySchema(schema = @Schema(implementation = Product.class)))
    })
    public List<Product> showAllProducts(HttpServletRequest request) {
        log.info("IN: showAllProducts - Request: [method:{}] URI: {}",
                request.getMethod(), request.getRequestURI());

        List<Product> allProducts = productJPAService.findAllProducts();

        log.info("OUT: showAllProducts - found {} products", allProducts.size());
        return allProducts;
    }

    @GetMapping("/products/{productId}")
    @Operation(summary = "Search product by it's ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found product by it's ID", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))
            }),
            @ApiResponse(responseCode = "400", description = "Incorrect parameter - ID cannot be negative or equal 0", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            }),
            @ApiResponse(responseCode = "404", description = "No such product with given ID", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            })
    })
    public Product showProductById(@PathVariable @Min(value = 1, message = "ID cannot be less or equal 0") Long productId,
                                   HttpServletRequest request) {
        log.info("IN: showProductById - Request: [method:{}] URI: {}",
                request.getMethod(), request.getRequestURI());

        Product product = productJPAService.findProductById(productId);

        log.info("OUT: showProductById - found {}", product);
        return product;
    }

    @PostMapping("/products")
    @Operation(summary = "Create new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))
            }),
            @ApiResponse(responseCode = "400", description = "Incorrect data input for product", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))
            })
    })
    public Product createProduct(@RequestBody @Valid Product product, HttpServletRequest request) {
        log.info("IN: createProduct - Request: [method:{}] URI: {}",
                request.getMethod(), request.getRequestURI());

        Product createdProduct = productJPAService.createProduct(product);

        log.info("OUT: createProduct - created {}", product);
        return createdProduct;
    }

    @PutMapping("/products/{productId}")
    @Operation(summary = "Update product or create a new one")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated or created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))
            }),
            @ApiResponse(responseCode = "400", description = "Incorrect data input for product", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))
            })
    })
    public Product updateProduct(@PathVariable @Min(value = 1, message = "ID cannot be less or equal 0") Long productId,
                                 @RequestBody @Valid Product product, HttpServletRequest request) {
        log.info("IN: updateProduct - Request: [method:{}] URI: {}",
                request.getMethod(), request.getRequestURI());

        product.setProductId(productId);
        Product updatedProduct = productJPAService.updateProduct(product);

        log.info("OUT: updateProduct - updated {}", updatedProduct);
        return product;
    }

    @GetMapping("/employees/{customerId}/orders/{orderId}/products")
    public List<Product> showProductsForSpecificOrder(
            @PathVariable @Min(value = 1, message = "ID cannot be less or equal 0") Long customerId,
            @PathVariable Long orderId, HttpServletRequest request) {
        log.info("IN: showProductsForSpecificOrder - Request: [method:{}] URI: {}",
                request.getMethod(), request.getRequestURI());

        List<Product> productList = productJPAService.findAllProductsByCustomerIdAndOrderId(customerId, orderId);

        log.info("OUT: showProductsForSpecificOrder - found {} products in order with ID={}", productList.size(), orderId);
        return productList;
    }

    @GetMapping("/employees/{customerId}/orders/{orderId}/products/{productId}")
    public Product showSpecificProductForSpecificOrder(@PathVariable Long customerId,
                                                       @PathVariable Long orderId,
                                                       @PathVariable Long productId, HttpServletRequest request) {
        log.info("IN: showSpecificProductForSpecificOrder - Request: [method:{}] URI: {}",
                request.getMethod(), request.getRequestURI());

        Product product = productJPAService.findSpecificProductByCustomerIdAndOrderId(customerId, orderId, productId);

        log.info("OUT: showSpecificProductForSpecificOrder - found {}", product);
        return product;
    }

}
