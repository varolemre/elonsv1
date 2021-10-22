package com.artnft.artnft.auth;

import com.artnft.artnft.dto.Views;
import com.artnft.artnft.entity.User;
import com.artnft.artnft.valitor.annotations.CurrentUser;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    @PostMapping("/auth")
    @JsonView(Views.Base.class)
    ResponseEntity<?> handleAuthentication(@CurrentUser User user){
        return ResponseEntity.ok(user);
    }

}
