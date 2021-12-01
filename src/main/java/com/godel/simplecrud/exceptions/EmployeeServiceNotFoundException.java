package com.godel.simplecrud.exceptions;

public class EmployeeServiceNotFoundException extends ServiceNotFoundException {

    public EmployeeServiceNotFoundException(String message) {
        super(message);
    }
}
