package com.blood.donation.app.exception;

public class TokenIOException extends UserException {
    public TokenIOException(String message) {
        super(TokenIOException.class.getSimpleName(),message);
    }
    public TokenIOException(Exception exception) {
        super(TokenIOException.class.getSimpleName(),exception);
    }
}
