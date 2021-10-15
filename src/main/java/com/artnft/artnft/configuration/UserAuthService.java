package com.artnft.artnft.configuration;

import com.artnft.artnft.entity.User;
import com.artnft.artnft.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> inDB = userRepository.findByUsername(username);
        if(!inDB.isPresent()){
            throw  new UsernameNotFoundException("User not found!");
        }
        return inDB.get();
    }
}

