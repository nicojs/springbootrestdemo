package com.infosupport.nicojs.springbootrestdemo.rest;


import com.infosupport.nicojs.springbootrestdemo.models.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@ControllerAdvice
@RequestMapping(produces = "application/json")
public class ErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity notFoundException(final NotFoundException e) {
        return error(e, HttpStatus.NOT_FOUND, e.toString());
    }

    private ResponseEntity<ApiError> error(final Exception exception, final HttpStatus httpStatus, final String logRef) {
        final String message = Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new ApiError(httpStatus, message, logRef), httpStatus);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> assertionException(final IllegalArgumentException e) {
        return error(e, HttpStatus.NOT_FOUND, e.getLocalizedMessage());
    }

}