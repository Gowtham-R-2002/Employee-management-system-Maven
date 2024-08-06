package com.i2i.ems.exceptions;

public class EmployeeException extends Exception {
    private static final long serialVersionUID = 1L;

    public EmployeeException(String message, Throwable throwable) {
        super(message, throwable);
    }
}