package com.artnft.artnft.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CustomNotFoundException extends RuntimeException {

    public CustomNotFoundException(String type) {
        super(type + " bulunamadı.");
    }
}