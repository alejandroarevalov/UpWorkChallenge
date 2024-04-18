package com.upwork.challenge.service;

import com.upwork.challenge.domain.User;
import com.upwork.challenge.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Should get user principal for security context")
    public void shouldGetUserPrincipalForSecurityContext() {
        // Given
        String userName = "alejandroarevalov@gmail.com";
        User user = new User(1L, userName, "123456", Collections.emptyList());
        // When
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));
        UserDetails principal = userService.loadUserByUsername(userName);
        // Then
        assertThat(principal).isNotNull();
        assertThat(principal).extracting("username").isEqualTo(userName);
        assertThat(principal).extracting("password").isNotNull();
        verify(userRepository).findByUserName(userName);
    }
    @Test
    @DisplayName("Should throw user not found exception")
    public void shouldThrowUserNotFoundException() {
        // Given
        String userName = "non-existinguser@gmail.com";
        // When
        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());
        // Then
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> userService.loadUserByUsername(userName))
                .withMessageMatching("No user found for username: " + userName);
        verify(userRepository).findByUserName(userName);
    }

}
