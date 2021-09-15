package com.blood.donation.app.exception;

public class TokenException extends UserException{
    public TokenException(Exception exception) {
        super(TokenException.class.getSimpleName(), exception);
    }
}
