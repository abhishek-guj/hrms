package com.roima.hrms.exceptions;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException() {
        super("Employee Not Found");
    }
}
