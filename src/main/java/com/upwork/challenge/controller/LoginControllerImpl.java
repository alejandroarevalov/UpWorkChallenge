package com.upwork.challenge.controller;

import com.upwork.challenge.dto.JwtTokenDTO;
import com.upwork.challenge.dto.UserDTO;
import com.upwork.challenge.errorhandling.UpWorkStudentsAuthenticationException;
import com.upwork.challenge.service.JwtService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;

public class LoginControllerImpl implements LoginController {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public LoginControllerImpl(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public JwtTokenDTO login(@RequestBody UserDTO userDto) {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDto.userName(),
                    userDto.password()
            );
            authenticationManager.authenticate(authToken);
            return new JwtTokenDTO(jwtService.generateToken(userDto.userName()));
        } catch (AccessDeniedException | BadCredentialsException exception) {
            throw new UpWorkStudentsAuthenticationException("Incorrect Username or password");
        }
    }
}
