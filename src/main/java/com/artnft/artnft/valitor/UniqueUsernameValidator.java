package com.artnft.artnft.valitor;

import com.artnft.artnft.entity.User;
import com.artnft.artnft.repository.UserRepository;
import com.artnft.artnft.valitor.annotations.UniqueUsername;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername,String> {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if(username != null){
            User byUsername = userRepository.findByUsername(username);
            if(byUsername != null){
                return false;
            }
        }

        return true;
    }
}
