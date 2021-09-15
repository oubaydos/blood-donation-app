package com.blood.donation.app.exception;

public class MissingAuthorizationHeaderException extends UserException {
    public MissingAuthorizationHeaderException() {
        super("MissingAuthorizationHeaderException","Authorization Header is either missing, or does not start with Bearer ");
    }
}
