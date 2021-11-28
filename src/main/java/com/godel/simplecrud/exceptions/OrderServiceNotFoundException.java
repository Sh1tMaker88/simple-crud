package com.godel.simplecrud.exceptions;

public class OrderServiceNotFoundException extends RuntimeException {
    public OrderServiceNotFoundException(String message) {
        super(message);
    }
}
