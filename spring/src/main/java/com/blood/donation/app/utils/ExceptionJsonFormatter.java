package com.blood.donation.app.utils;

import com.blood.donation.app.exception.UserException;

public record ExceptionJsonFormatter(String code, String message) {
    public static ExceptionJsonFormatter convertExceptionToJson(UserException exception) {
        return new ExceptionJsonFormatter(exception.getCode(), exception.getMessage());
    }
}
