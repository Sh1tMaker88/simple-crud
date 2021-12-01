package com.godel.simplecrud.controller;

import com.godel.simplecrud.exceptions.ErrorMessage;
import com.godel.simplecrud.model.Order;
import com.godel.simplecrud.model.Product;
import com.godel.simplecrud.service.OrderJPAService;
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
@Tag(name = "Order controller")
@Validated
public class OrderController {

    private final OrderJPAService orderJPAService;

    public OrderController(OrderJPAService orderJPAService) {
        this.orderJPAService = orderJPAService;
    }

    @GetMapping("/orders")
    @Operation(summary = "Show all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found orders", content = {
                    @Content(mediaType = "application/json", array =
                    @ArraySchema(schema = @Schema(implementation = Order.class)))
            })
    })
    public List<Order> showAllOrders(HttpServletRequest request) {
        log.info("IN: showAllOrders - Request: [method:{}] URI: {}",
                request.getMethod(), request.getRequestURI());

        List<Order> allOrders = orderJPAService.findAllOrders();

        log.info("OUT: showAllOrders - found {} orders", allOrders.size());
        return allOrders;
    }


    @GetMapping("/employees/{customerId}/orders")
    @Operation(summary = "Show all orders for specific customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found orders for given customer ID", content = {
                    @Content(mediaType = "application/json", array =
                    @ArraySchema(schema = @Schema(implementation = Order.class)))
            }),
            @ApiResponse(responseCode = "400", description = "Incorrect parameter - ID cannot be negative or equal 0", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            }),
            @ApiResponse(responseCode = "404", description = "No such customer with given ID", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            })
    })
    public List<Order> showOrdersByCustomer(@PathVariable @Min(value = 1, message = "ID cannot be less or equal 0") Long customerId,
                                            HttpServletRequest request) {
        log.info("IN: showOrdersByCustomer - Request: [method:{}] URI: {}",
                request.getMethod(), request.getRequestURI());

        List<Order> ordersByCustomer = orderJPAService.findAllOrdersByCustomerId(customerId);

        log.info("OUT: showOrdersByCustomer - found {} orders for customer with ID={}", ordersByCustomer.size(), customerId);
        return ordersByCustomer;
    }


    @GetMapping("/employees/{customerId}/orders/{orderId}")
    @Operation(summary = "Show specific orders for customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found order for given customer ID and order ID", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))
            }),
            @ApiResponse(responseCode = "400", description = "Incorrect parameter - ID cannot be negative or equal 0", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            }),
            @ApiResponse(responseCode = "404", description = "No such customer/order with given ID", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            })
    })
    public Order showOrderById(@PathVariable @Min(value = 1, message = "ID cannot be less or equal 0") Long customerId,
                               @PathVariable @Min(value = 1, message = "ID cannot be less or equal 0") Long orderId,
                               HttpServletRequest request) {
        log.info("IN: showOrderById - Request: [method:{}] URI: {}",
                request.getMethod(), request.getRequestURI());

        Order order = orderJPAService.findOrderByCustomerIdAndOrderId(customerId, orderId);

        log.info("OUT: showOrderById - found {}", order);
        return order;
    }

    @PostMapping("/employees/{customerId}/orders")
    @Operation(summary = "Create order for customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created order", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))
            }),
            @ApiResponse(responseCode = "400", description = "Incorrect parameter - ID cannot be negative or equal 0", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            }),
            @ApiResponse(responseCode = "404", description = "Incorrect data input for order", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            })
    })
    public Order createOrderForCustomer(@PathVariable @Min(value = 1, message = "ID cannot be less or equal 0") Long customerId,
                                        @RequestBody @Valid Order order,
                                        HttpServletRequest request) {
        log.info("IN: createOrderForCustomer - Request: [method:{}] URI: {}",
                request.getMethod(), request.getRequestURI());

        Order createdOrder = orderJPAService.createOrderForCustomer(customerId, order);

        log.info("OUT: createOrderForCustomer - created {}", createdOrder);
        return createdOrder;
    }


    @PutMapping("/employees/{customerId}/orders/{orderId}")
    @Operation(summary = "Update or create order for customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created/updated order", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))
            }),
            @ApiResponse(responseCode = "400", description = "Incorrect parameter - ID cannot be negative or equal 0", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            }),
            @ApiResponse(responseCode = "404", description = "Incorrect data input for order", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            })
    })
    public Order updateOrderForCustomer(
            @PathVariable @Min(value = 1, message = "ID cannot be less or equal 0") Long customerId,
            @PathVariable @Min(value = 1, message = "ID cannot be less or equal 0") Long orderId,
            @RequestBody @Valid Order order,
            HttpServletRequest request) {
        log.info("IN: updateOrderForCustomer - Request: [method:{}] URI: {}",
                request.getMethod(), request.getRequestURI());

        order.setOrderId(orderId);
        Order updatedOrder = orderJPAService.updateOrderForCustomer(customerId, order);

        log.info("OUT: updateOrderForCustomer - updated {}", order);
        return updatedOrder;
    }
}
