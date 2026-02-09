package com.roima.hrms.exceptions;

public class TravelTypeNotFoundException extends RuntimeException {

    public TravelTypeNotFoundException() {
        super("TravelType not found.");
    }
}
