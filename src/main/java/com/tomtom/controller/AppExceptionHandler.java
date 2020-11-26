package com.tomtom.controller;

import com.tomtom.exception.AppException;
import com.tomtom.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Calendar;

@RestControllerAdvice
@Slf4j
public class AppExceptionHandler {

    @ExceptionHandler(value = {AppException.class})
    public ResponseEntity<Object> handleAppException(AppException exception) {
        return ResponseEntity.status(exception.getErrorResponse().getStatus())
                .body(exception.getErrorResponse());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleException(Exception exception) {
        log.error("Caught exception : ", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .errorMsg(exception.getMessage())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .timestamp(Calendar.getInstance().getTime())
                        .build());
    }
}
