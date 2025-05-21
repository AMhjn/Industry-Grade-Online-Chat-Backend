package com.abhir.chat.user.service;

import com.abhir.chat.user.entity.User;
import com.abhir.chat.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Just create a minimal UserDetails with no password
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password("") // no password needed here
                .authorities("USER")
                .build();
    }

}
