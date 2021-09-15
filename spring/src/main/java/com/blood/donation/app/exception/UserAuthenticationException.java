package com.blood.donation.app.exception;

public class UserAuthenticationException extends UserException{
    public UserAuthenticationException(String message) {
        super("AuthenticationException", message);
    }

    public UserAuthenticationException(Exception exception) {
        super("AuthenticationException", exception);
    }
}
