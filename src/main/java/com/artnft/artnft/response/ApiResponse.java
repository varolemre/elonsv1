package com.artnft.artnft.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.sql.Timestamp;

import static org.springframework.web.servlet.HandlerMapping.LOOKUP_PATH;

@Data
@Builder
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ApiResponse {
    private Timestamp timestamp;
    private int status;
    private Object message;
    private Object path;
    private Object data;

    public static ResponseEntity<ApiResponse> responseOk(Object data) {
        RequestAttributes reqAttributes = RequestContextHolder.currentRequestAttributes();
        return ResponseEntity.ok(ApiResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .path(reqAttributes.getAttribute(LOOKUP_PATH, 0))
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(data)
                .build());
    }

    public static ResponseEntity<ApiResponse> responseCreated(Object data) {
        RequestAttributes reqAttributes = RequestContextHolder.currentRequestAttributes();
        return ResponseEntity.ok(ApiResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .path(reqAttributes.getAttribute(LOOKUP_PATH, 0))
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(data)
                .build());
    }
}
