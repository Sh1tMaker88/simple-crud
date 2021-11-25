package com.godel.simplecrud.exceptions;

public class ProductServiceNotFoundException extends RuntimeException {

    public ProductServiceNotFoundException(String message) {
        super(message);
    }
}
