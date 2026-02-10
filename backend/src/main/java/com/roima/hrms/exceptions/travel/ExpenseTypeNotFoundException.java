package com.roima.hrms.exceptions.travel;

public class ExpenseTypeNotFoundException extends RuntimeException {

    public ExpenseTypeNotFoundException() {
        super("Expense Type not found.");
    }
}
