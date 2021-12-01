package com.godel.simplecrud.exceptions;

public class OrderServiceNotFoundException extends ServiceNotFoundException {
    public OrderServiceNotFoundException(String message) {
        super(message);
    }
}
