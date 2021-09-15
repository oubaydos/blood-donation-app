package com.blood.donation.app.exception;

public abstract class UserValidationException extends UserException{
    public UserValidationException(String message) {
        super("Validation error", message);
    }

    public UserValidationException(String message, Throwable cause) {
        super("Validation error", message, cause);
    }

}
