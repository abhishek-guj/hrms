package com.roima.hrms.exceptions.travel;

public class TravelDocumentTypeNotFoundException extends RuntimeException {

    public TravelDocumentTypeNotFoundException() {
        super("Travel Document Type not found.");
    }
}
