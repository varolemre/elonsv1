package com.artnft.artnft.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;

public class AuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());

        //todo
    }


    @SneakyThrows
    private String getErrorMap(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        LinkedHashMap<String, Object> errorMap = new LinkedHashMap<>();
        Timestamp myTimestamp = new Timestamp(System.currentTimeMillis());
        String timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(myTimestamp);
        errorMap.put("timestamp", timestamp);
        errorMap.put("status", String.valueOf(response.getStatus()));
        errorMap.put("error", "Forbidden");
        errorMap.put("message", authException.getMessage());
        errorMap.put("path", request.getRequestURI());
        return new ObjectMapper().writeValueAsString(errorMap);
    }
}

