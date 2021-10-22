package com.artnft.artnft.configuration;

import com.artnft.artnft.exception.CustomNotFoundException;
import com.artnft.artnft.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
@RestControllerAdvice
public class MyControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse handleCustomNotFoundException(Exception ex, ServletWebRequest request) {
        log.error("Error in handleException: {}", ex.getMessage());
        return ApiResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .path(request.getRequest().getRequestURI())
                .build();
    }

    @ExceptionHandler(CustomNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse handleException(CustomNotFoundException ex, ServletWebRequest request) {
        log.error("Error in handleException: {}", ex.getMessage());
        return ApiResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .path(request.getRequest().getRequestURI())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handledValidationException(MethodArgumentNotValidException ex, ServletWebRequest request) {
        log.error("Error in handledValidationException: {}", ex.getMessage());
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ApiResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(HttpStatus.BAD_REQUEST.value())
                .message(validationErrors)
                .path(request.getRequest().getRequestURI())
                .build();
    }

}
