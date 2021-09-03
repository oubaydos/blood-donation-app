package com.blood.donation.app;

import com.blood.donation.app.dto.UserError;
import com.blood.donation.app.exception.UserEmailDuplicatedException;
import org.springframework.http.HttpStatus;

import java.time.Instant;

public class Utils {
    public static void main(String[] args) {
        UserError err = new UserError(
                HttpStatus.BAD_REQUEST,
                Instant.now(),
                new UserEmailDuplicatedException("oubayda@g")
                );
        System.err.println(err);
    }
}
