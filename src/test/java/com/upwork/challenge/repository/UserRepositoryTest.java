package com.upwork.challenge.repository;

import com.upwork.challenge.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    @DisplayName("Should retrieve an existing user")
    public void shouldRetrieveAnExistingUser() {
        // Given
        String username = "alejandroarevalov@gmail.com";
        // When
        User expectedUser = userRepository.findByUserName(username).orElse(null);
        // Then
        assertThat(expectedUser).isNotNull();
        assertThat(expectedUser).extracting("userName")
                .isEqualTo(username);
    }

    @Test
    @DisplayName("Should not retrieve an existing user")
    public void shouldNotRetrieveAnExistingUser() {
        // Given
        String username = "eduardo.giraldo@gmail.com";
        // When
        User expectedUser = userRepository.findByUserName(username).orElse(null);
        // Then
        assertThat(expectedUser).isNull();
    }
}
