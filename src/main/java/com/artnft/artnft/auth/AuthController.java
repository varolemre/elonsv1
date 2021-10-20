package com.artnft.artnft.auth;

import com.artnft.artnft.CurrentUser;
import com.artnft.artnft.dto.Views;
import com.artnft.artnft.entity.User;
import com.artnft.artnft.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    UserRepository userRepository;


    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/auth")
    @JsonView(Views.Base.class)
    ResponseEntity<?> handleAuthentication(@CurrentUser User user){
        return ResponseEntity.ok(user);
    }


}
