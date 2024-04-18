package com.upwork.challenge.controller;

import com.upwork.challenge.dto.JwtTokenDTO;
import com.upwork.challenge.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface LoginController {

    @PostMapping("/login")
    JwtTokenDTO login(@RequestBody UserDTO userDto);
}
