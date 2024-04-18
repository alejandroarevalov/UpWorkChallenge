package com.upwork.challenge.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtServiceTest {

    private final JwtServiceImpl jwtService;

    public JwtServiceTest() {
        jwtService = new JwtServiceImpl();
    }

    @Test
    @DisplayName("Should generate a new token successfully")
    public void shouldCreateANewTokenSuccessfully() {
        // Given
        String username = "cecikumara@gmail.com";
        // when
        String token = jwtService.generateToken(username);
        // Then
        assertThat(token).isNotNull();
        assertThat(token).hasSizeGreaterThan(50);
    }

    @Test
    @DisplayName("Should extract username from JWT token successfully")
    public void shouldExtractUserNameFromTokenSuccesfully() {
        // Given
        String expectedUsername = "cecikumara@gmail.com";
        String token = jwtService.generateToken(expectedUsername);
        // when
        String username = jwtService.extractUsername(token);
        // Then
        assertThat(username).isEqualTo("cecikumara@gmail.com");
    }

    @Test
    @DisplayName("Should extract expiration date from JWT token successfully")
    public void shouldExtractExpirationDateSuccessfully() {
        // Given
        String expectedUsername = "cecikumara@gmail.com";
        String token = jwtService.generateToken(expectedUsername);
        // when
        Date expirationDate = jwtService.extractExpiration(token);
        // Then
        assertThat(expirationDate).isNotNull();
        assertThat(expirationDate).hasDayOfMonth(LocalDateTime.now().getDayOfMonth());
    }

    @Test
    @DisplayName("Should validate JWT token successfully")
    public void shouldValidateTokenSuccessfully() {
        // Given
        String expectedUsername = "cecikumara@gmail.com";
        String token = jwtService.generateToken(expectedUsername);
        // when
        boolean valid = jwtService.validateToken(token, new User(expectedUsername, "1234", Collections.emptyList()));
        // Then
        assertThat(valid).isTrue();
    }

    @Test
    @DisplayName("Should not validate JWT token successfully")
    public void shouldNotValidateTokenSuccessfully() {
        // Given
        String expectedUsername = "cecikumara@gmail.com";
        String token = jwtService.generateToken(expectedUsername);
        // when
        boolean valid = jwtService.validateToken(token, new User("tokenstoled@gmail.com", "1234", Collections.emptyList()));
        // Then
        assertThat(valid).isFalse();
    }
}
