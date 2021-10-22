package com.artnft.artnft.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomNotAllowedException extends RuntimeException {

    public CustomNotAllowedException(String type) {
        super(type);
    }
}