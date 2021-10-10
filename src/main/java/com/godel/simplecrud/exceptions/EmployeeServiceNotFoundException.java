package com.godel.simplecrud.exceptions;

public class EmployeeServiceNotFoundException extends RuntimeException {

    public EmployeeServiceNotFoundException(String message) {
        super(message);
    }
}
