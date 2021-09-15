package com.blood.donation.app.controller;


import com.blood.donation.app.dto.UserError;
import com.blood.donation.app.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

import static com.blood.donation.app.utils.ExceptionJsonFormatter.convertExceptionToJson;

// YOU NEED TO HANDLE BindException Errors
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(assignableTypes = UserController.class)
public class UserControllerAdvice {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @ExceptionHandler(UserException.class)
    public ResponseEntity<UserError> handleException(
            UserException exception
    ) {
        return handleUserException(HttpStatus.BAD_REQUEST.value(), exception);
    }
    @ExceptionHandler(UserEmailDuplicatedException.class)
    public ResponseEntity<UserError> handleException(
            UserEmailDuplicatedException exception
    ) {
        return handleUserException(HttpStatus.BAD_REQUEST.value(), exception);
    }
    @ExceptionHandler(UserAuthenticationException.class)
    public ResponseEntity<UserError> handleException(
            UserAuthenticationException exception
    ) {
        return handleUserException(HttpStatus.UNAUTHORIZED.value(), exception);
    }
    @ExceptionHandler(TokenIOException.class)
    public ResponseEntity<UserError> handleException(
            TokenIOException exception
    ) {
        return handleUserException(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception);
    }
    @ExceptionHandler(MissingAuthorizationHeaderException.class)
    public ResponseEntity<UserError> handleException(
            MissingAuthorizationHeaderException exception
    ) {
        return handleUserException(HttpStatus.UNAUTHORIZED.value(), exception);
    }
    @ExceptionHandler(TokenException.class)
    public ResponseEntity<UserError> handleException(
            TokenException exception
    ) {
        return handleUserException(HttpStatus.UNAUTHORIZED.value(), exception);
    }


    private <E extends UserException> ResponseEntity<UserError> handleUserException(
            int statusCode, E exception) {

        LOG.debug("error response {}", exception.getMessage());
        UserError err = new UserError(
                HttpStatus.resolve(statusCode),
                Instant.now(),
                convertExceptionToJson(exception)
        );
        return ResponseEntity
                .status(statusCode)
                .body(err);

    }
}
