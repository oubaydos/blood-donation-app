package com.blood.donation.app.dto;

import com.blood.donation.app.exception.UserException;
import com.blood.donation.app.utils.ExceptionJsonFormatter;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@JsonFormat
public record UserError(
        HttpStatus status,
        //change GMT+1 to GMT if not summer
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", timezone = "GMT+1")
        Instant timestamp,
        ExceptionJsonFormatter exception
//        String code,
//        String message
) {
    // message to
//    {
//        message :
//    }
//    or
//    {
//        error : {
//            message :
//            somethingElse :
//        }
//    }
//    check https://www.toptal.com/java/spring-boot-rest-api-error-handling
}
