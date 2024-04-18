package com.upwork.challenge.repository;

import com.upwork.challenge.domain.Activity;
import com.upwork.challenge.domain.Classroom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ActivityRepositoryTest {

    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityRepositoryTest(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Test
    @DisplayName("Should retrieve all activities by classroom")
    public void shouldRetrieveAllActivitiesByClassroom() {
        //Given
        String qrCode = "313fda6e61d9cd405800d1e8c26096f5";
        Classroom mockedClassroom = new Classroom(3L, qrCode, null);
        //When
        List<Activity> expectedActivitiesList = activityRepository.findByClassroom(mockedClassroom);
        //Then
        assertThat(expectedActivitiesList.size()).isEqualTo(3L);
        assertThat(expectedActivitiesList).extracting("code")
                .contains("CKNGI", "SPNII", "SPNI");

    }

    @Test
    @DisplayName("Should retrieve no activities by classroom")
    public void shouldRetrieveNoActivitiesByClassroom() {
        //Given
        String qrCode = "313fda6e61d9cd405800d1e8c26096f4";
        Classroom mockedClassroom = new Classroom(4L, qrCode, null);
        //When
        List<Activity> expectedActivitiesList = activityRepository.findByClassroom(mockedClassroom);
        //Then
        assertThat(expectedActivitiesList).isEmpty();
    }

    @Test
    @DisplayName("Should retrieve an existing activity")
    public void shouldRetrieveAnExistingActivity() {
        //Given
        String activityCode = "CKNGI";
        //When
        Activity expectedActivity = activityRepository.findByCode(activityCode).orElse(null);
        //Then
        assertThat(expectedActivity).isNotNull();
        assertThat(expectedActivity).extracting("name")
                .isEqualTo("Cooking I");
    }

    @Test
    @DisplayName("Should not retrieve an existing activity")
    public void shouldNotRetrieveAnExistingActivity() {
        //Given
        String activityCode = "CKNGIIIII";
        //When
        Activity expectedActivity = activityRepository.findByCode(activityCode).orElse(null);
        //Then
        assertThat(expectedActivity).isNull();
    }

}
