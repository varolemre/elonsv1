package com.artnft.artnft.configuration;

import com.artnft.artnft.entity.User;
import com.artnft.artnft.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User inDB = userRepository.findByUsername(username);
        if(inDB==null){
            throw  new UsernameNotFoundException("User not found!");
        }
        return inDB;
    }
}

