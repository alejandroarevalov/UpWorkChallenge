package com.upwork.challenge.controller;

import com.upwork.challenge.dto.JwtTokenDTO;
import com.upwork.challenge.dto.UserDTO;
import com.upwork.challenge.service.JwtService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    @InjectMocks
    private LoginControllerImpl loginController;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;

    @Test
    @DisplayName("Should allow user to login successfully")
    public void shouldAllowUserToLoginSuccessfully() {
        // Given
        UserDTO userDTO = new UserDTO("julianasamp89@mail.com", "123123");
        String expectedToken = "3nCrYpT3d.t0k3n";
        // When
        when(jwtService.generateToken(userDTO.userName())).thenReturn(expectedToken);
        JwtTokenDTO tokenDTO = loginController.login(userDTO);
        // Then
        assertThat(tokenDTO).isNotNull();
        assertThat(tokenDTO).extracting("token").isEqualTo(expectedToken);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken(userDTO.userName());
    }

    @Test
    @DisplayName("Should throw error when login is attempted")
    public void shouldThrowErrorWhenLoginIsAttempted() {
        // Given
        UserDTO userDTO = new UserDTO("uknownUser@mail.com", "XXxxXX");
        // When
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(BadCredentialsException.class);
        // Then
        assertThatExceptionOfType(RuntimeException.class)
            .isThrownBy(() -> loginController.login(userDTO))
        .withMessageMatching("Incorrect Username or password");
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }


}
