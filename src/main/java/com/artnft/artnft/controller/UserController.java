package com.artnft.artnft.controller;

import com.artnft.artnft.dto.UserDto;
import com.artnft.artnft.dto.UserRegisterDto;
import com.artnft.artnft.dto.UserUptadeDto;
import com.artnft.artnft.dto.Views;
import com.artnft.artnft.entity.Followers;
import com.artnft.artnft.entity.Market;
import com.artnft.artnft.entity.Nft;
import com.artnft.artnft.entity.User;
import com.artnft.artnft.response.ApiResponse;
import com.artnft.artnft.service.UserService;
import com.artnft.artnft.valitor.annotations.CurrentUser;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ModelMapper mapper;

    @PostMapping
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        User user = mapper.map(userRegisterDto, User.class);
        userService.saveUser(user);
        return ApiResponse.responseCreated(true);
    }

    @GetMapping("/{id}")
    @JsonView(Views.Base.class)
    public User getUserById(@PathVariable Long id) {
        return userService.getUsers(id);
    }

    @Transactional
    @GetMapping("/username/{username}")
    public UserDto getUserByUsername(@PathVariable String username) {
        User user = userService.getByUsername(username);
        return new UserDto(user);
    }

    @PutMapping("/edit/{username}")
    @PreAuthorize("#username == principal.username")
    public UserDto uptadeUser(@RequestBody UserUptadeDto userUptadeDto, @PathVariable String username) {
        User user = userService.uptadeUser(username, userUptadeDto);
        return new UserDto(user);
    }

    @PostMapping("/follow/{username}")
    public ResponseEntity<?> followUser(@PathVariable String username, @CurrentUser User user) {
        return userService.followUser(username, user);
    }

    @GetMapping("/{username}/followers")
    public List<Followers> getUserFollowers(@PathVariable String username) {
        return userService.getUserFollowers(username);
    }

    @GetMapping("/{username}/follow")
    public Long getUsersFollowersNumber(@PathVariable String username) {
        return userService.getFollowerNumber(username);
    }

    @GetMapping("/fn")
    public Long getUsersFollowingNumber(@CurrentUser User user) {
        return userService.getFollowingNumber(user);
    }

    @GetMapping("/{username}/fc")
    public boolean checkFollow(@PathVariable String username, @CurrentUser User user) {
        return userService.checkFollow(username, user);
    }

    @GetMapping("/nft")
    public List<Nft> getUserNfts(@CurrentUser User user) {
        return userService.getUserNfts(user);
    }

    @GetMapping("/onsale")
    public List<Market> getUserNftOnSale(@CurrentUser User user) {
        return userService.getUserNftOnSale(user);
    }


}
