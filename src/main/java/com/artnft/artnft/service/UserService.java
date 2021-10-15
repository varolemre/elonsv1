package com.artnft.artnft.service;

import com.artnft.artnft.dto.GenericResponse;
import com.artnft.artnft.entity.User;
import com.artnft.artnft.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public GenericResponse saveUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setWalletId(UUID.randomUUID().toString());
        userRepository.save(user);
        return new GenericResponse("User Created");
    }

    public User getUsers(Long id) {
       return userRepository.findById(id).orElseThrow();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public User getUserByWalletId(String walletId){
        return userRepository.findBywalletId(walletId);
    }

}
