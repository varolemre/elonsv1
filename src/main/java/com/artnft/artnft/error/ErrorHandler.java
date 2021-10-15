package com.artnft.artnft.error;

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
public class ErrorHandler implements ErrorController {

    @Autowired
    private ErrorAttributes errorAttributes;

    public String error() {
        // handle error
        // ..
        return "/error";
    }

    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    ApiError handleError(WebRequest webRequest){
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE, ErrorAttributeOptions.Include.BINDING_ERRORS));
        String message= (String)errorAttributes.get("message");
        String path = (String)errorAttributes.get("path");
        int status = (Integer)errorAttributes.get("status");
        System.out.println("ERROR ATT: "+errorAttributes);
        ApiError error = new ApiError(status,message,path);
        if(errorAttributes.containsKey("errors") && errorAttributes.containsKey("exception")) {
            @SuppressWarnings("unchecked")
            List<FieldError> fieldErrors = (List<FieldError>) errorAttributes.get("errors");
            System.out.println("fiedsss=== " + fieldErrors);
            Map<String, String> validationErrors = new HashMap<>();
            for (FieldError fieldError : fieldErrors) {
                validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }

            error.setValidationErrors(validationErrors);
        }
        return error;
    }


}

