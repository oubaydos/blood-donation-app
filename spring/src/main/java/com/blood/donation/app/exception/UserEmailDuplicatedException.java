package com.blood.donation.app.exception;


import com.fasterxml.jackson.annotation.JsonFormat;


public class UserEmailDuplicatedException extends UserValidationException {
    public UserEmailDuplicatedException(String email) {
        super("an account already exists for this email : " + email);
    }
}
