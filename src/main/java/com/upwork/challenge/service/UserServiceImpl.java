package com.upwork.challenge.service;

import com.upwork.challenge.domain.User;
import com.upwork.challenge.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUserName(email);
        User user = userOptional.orElseThrow(
            () -> new UsernameNotFoundException("No user found for username: " + email)
        );
        return new org.springframework.security.core.userdetails.User(
            user.getUserName(),
            user.getPassword(),
            Collections.emptyList()
        );
    }
}
