package com.artnft.artnft.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ErrorHandler implements ErrorController {
    private final ErrorAttributes errorAttributes;

    @RequestMapping("/error")
    public ApiError handleError(WebRequest webRequest){
        Map<String, Object> errors = this.errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE, ErrorAttributeOptions.Include.BINDING_ERRORS));
        String message= String.valueOf(errors.get("message"));
        String path = String.valueOf(errors.get("path"));
        int status = (Integer)errors.get("status");
        log.error("ERROR ATT: "+errors);
        ApiError error = new ApiError(status,message,path);
        if(errors.containsKey("errors") && errors.containsKey("exception")) {
            @SuppressWarnings("unchecked")
            List<FieldError> fieldErrors = (List<FieldError>) errors.get("errors");
            Map<String, String> validationErrors = new HashMap<>();
            for (FieldError fieldError : fieldErrors) {
                validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            error.setValidationErrors(validationErrors);
        }
        return error;
    }


}

