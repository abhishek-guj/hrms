package com.roima.hrms.exceptions.travel;

public class TravelTypeNotFoundException extends RuntimeException {

    public TravelTypeNotFoundException() {
        super("TravelType not found.");
    }
}
