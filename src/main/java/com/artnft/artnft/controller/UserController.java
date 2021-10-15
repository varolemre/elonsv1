package com.artnft.artnft.controller;

import com.artnft.artnft.dto.GenericResponse;
import com.artnft.artnft.dto.UserProjection;
import com.artnft.artnft.dto.Views;
import com.artnft.artnft.entity.User;
import com.artnft.artnft.error.ApiError;
import com.artnft.artnft.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public GenericResponse createUser(@Valid @RequestBody User user){
        return userService.saveUser(user);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handledValidationException(MethodArgumentNotValidException exception){
        ApiError error = new ApiError(400,"validation error","/users");
        Map<String,String> validationErrors = new HashMap<>();
        for(FieldError fieldError: exception.getBindingResult().getFieldErrors()){
            validationErrors.put(fieldError.getField(),fieldError.getDefaultMessage());
        }
        error.setValidationErrors(validationErrors);
        return error;
    }

    @GetMapping("/get/{id}")
    @JsonView(Views.Base.class)
    public User getUsers(@PathVariable Long id){
        return userService.getUsers(id);
    }



}
