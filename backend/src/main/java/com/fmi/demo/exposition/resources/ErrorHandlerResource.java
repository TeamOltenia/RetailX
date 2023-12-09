package com.fmi.demo.exposition.resources;


import com.fmi.demo.exposition.exceptions.CustomErrorHandler;
import com.fmi.demo.exposition.exceptions.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ErrorHandlerResource {

    @ExceptionHandler(CustomErrorHandler.class)
    public ResponseEntity handleException(CustomErrorHandler e) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorDetails.builder()
                                .errorKey(e.getErrorDispatcher().getErrorKey())
                                .detail(e.getErrorDispatcher().getErrorMessage())
                                .errorDate(ZonedDateTime.now())
                                .build());
    }
}