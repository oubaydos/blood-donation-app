package com.blood.donation.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@JsonFormat
public record Error(
        HttpStatus status,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
        Instant timestamp,
        String code,
        String message
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
