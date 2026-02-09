package com.roima.hrms.exceptions;

public class AuthorizationHeaderMissing extends RuntimeException {
    public AuthorizationHeaderMissing() {
        super("Authorization header is missing or invalid");
    }
}
