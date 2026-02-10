package com.roima.hrms.exceptions.travel;

public class TravelPlanNotFoundException extends RuntimeException {

    public TravelPlanNotFoundException() {
        super("TravelPlan not found.");
    }
}
