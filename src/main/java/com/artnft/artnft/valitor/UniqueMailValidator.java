package com.artnft.artnft.valitor;

import com.artnft.artnft.entity.User;
import com.artnft.artnft.repository.UserRepository;
import com.artnft.artnft.valitor.annotations.UniqueMail;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueMailValidator implements ConstraintValidator<UniqueMail,String> {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(String mail, ConstraintValidatorContext context) {
        if(mail != null){
            Optional<User> byUsername = userRepository.findByMail(mail);
            if(byUsername.isPresent()){
                return false;
            }
        }

        return true;
    }
}
