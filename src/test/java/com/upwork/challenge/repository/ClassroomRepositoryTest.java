package com.upwork.challenge.repository;

import com.upwork.challenge.domain.Classroom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ClassroomRepositoryTest {

    private final ClassroomRepository classroomRepository;

    @Autowired
    public ClassroomRepositoryTest (ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    @Test
    @DisplayName("Should retrieve an existing classroom")
    public void shouldRetrieveAnExistingClassroom() {
        // Given
        String qrCode = "b098078be0c4a994e3214daf50b5ba57";
        // When
        Classroom expected = classroomRepository.findByQrCode(qrCode).orElse(null);
        // Then
        assertThat(expected).isNotNull();
        assertThat(expected).extracting("qrCode")
                .isEqualTo(qrCode);

    }

    @Test
    @DisplayName("Should not retrieve an existing classroom")
    public void shouldNotRetrieveAnExistingClassroom() {
        // Given
        String qrCode = "b098078be0c4a994ed214daf50b5ba57";
        // When
        Classroom expected = classroomRepository.findByQrCode(qrCode).orElse(null);
        // Then
        assertThat(expected).isNull();

    }
}
