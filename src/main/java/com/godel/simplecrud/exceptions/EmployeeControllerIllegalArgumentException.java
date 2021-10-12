package com.godel.simplecrud.exceptions;

public class EmployeeControllerIllegalArgumentException extends RuntimeException{

    public EmployeeControllerIllegalArgumentException(String message) {
        super(message);
    }
}
