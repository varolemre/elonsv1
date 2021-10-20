package com.artnft.artnft.controller;

import com.artnft.artnft.CurrentUser;
import com.artnft.artnft.dto.*;
import com.artnft.artnft.entity.Followers;
import com.artnft.artnft.entity.Market;
import com.artnft.artnft.entity.Nft;
import com.artnft.artnft.entity.User;
import com.artnft.artnft.error.ApiError;
import com.artnft.artnft.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

//    @GetMapping("/get/{username}")
//    @JsonView(Views.Base.class)
//    public Optional<User> getUserByUsername(@PathVariable String username){
//        return userService.getUserByUsername(username);
//    }

    @Transactional
    @GetMapping("/gets/{username}")
    public UserDto getUser(@PathVariable String username){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        User user =  userService.getByUsername(username);
      return  new UserDto(user);
    }

    @PutMapping("/edit/{username}")
    @PreAuthorize("#username == principal.username")
    public UserDto uptadeUser(@RequestBody UserUptadeDto userUptadeDto , @PathVariable String username){
        System.out.println("girdi");
         User user = userService.uptadeUser(username,userUptadeDto);
         return new UserDto(user);
    }

    @PostMapping("/follow/{username}")
    public ResponseEntity<?> followUser(@PathVariable String username, @CurrentUser User user){
        return userService.followUser(username,user);
    }

    @GetMapping("/{username}/followers")
    public List<Followers> getUserFollowers(@PathVariable String username){
        return userService.getUserFollowers(username);
    }

    @GetMapping("/{username}/follow")
    public Long getUsersFollowersNumber(@PathVariable String username){
        return userService.getFollowerNumber(username);
    }
    @GetMapping("/fn")
    public Long getUsersFollowingNumber(@CurrentUser User user){
        return userService.getFollowingNumber(user);
    }

    @GetMapping("/{username}/fc")
    public boolean checkFollow(@PathVariable String username, @CurrentUser User user){
        return userService.checkFollow(username,user);
    }

    @GetMapping("/nft")
    public List<Nft> getUserNfts(@CurrentUser User user){
        return userService.getUserNfts(user);
    }

    @GetMapping("/onsale")
    public List<Market> getUserNftOnSale(@CurrentUser User user){
        return userService.getUserNftOnSale(user);
    }


}
