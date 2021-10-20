package com.artnft.artnft.entity;

import com.artnft.artnft.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

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
