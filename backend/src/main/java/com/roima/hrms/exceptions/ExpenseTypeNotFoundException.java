package com.roima.hrms.exceptions;

public class ExpenseTypeNotFoundException extends RuntimeException {

    public ExpenseTypeNotFoundException() {
        super("Expense Type not found.");
    }
}
