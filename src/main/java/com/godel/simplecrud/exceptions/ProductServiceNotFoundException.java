package com.godel.simplecrud.exceptions;

public class ProductServiceNotFoundException extends ServiceNotFoundException {

    public ProductServiceNotFoundException(String message) {
        super(message);
    }
}
