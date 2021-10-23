package com.artnft.artnft.controller;

import com.artnft.artnft.dto.TransferRequest;
import com.artnft.artnft.entity.User;
import com.artnft.artnft.error.ApiError;
import com.artnft.artnft.service.TransferService;
import com.artnft.artnft.valitor.annotations.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/send")
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<?> transfer(@RequestBody TransferRequest transferRequest, @CurrentUser User user) {
        return transferService.sendNft(transferRequest, user);
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handledValidationException(Exception exception) {
        ApiError error = new ApiError(400, "validation error", "/send");
        Map<String, String> validationErrors = new HashMap<>();
        validationErrors.put("Error", "Error");
        error.setValidationErrors(validationErrors);
        return error;
    }


}
