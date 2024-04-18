package com.upwork.challenge.mapper;

import com.upwork.challenge.domain.Activity;
import com.upwork.challenge.dto.ActivityDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ActivityMapperTest {

    private ActivityMapper activityMapper;

    public ActivityMapperTest() {
        activityMapper = new ActivityMapper();
    }

    @Test
    @DisplayName("Should map activity entity to activity DTO")
    public void shouldMapActivityEntityToActivityDTO() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Activity activity = new Activity(1L, "Activity1", now.plusHours(1), now.plusHours(3),
                "Code1", null, null);
        // When
        ActivityDTO activityDTO = activityMapper.mapEntityToDTO(activity);
        // Then
        assertThat(activityDTO).isNotNull();
        assertThat(activityDTO)
                .extracting("name", "startDate", "endDate", "code")
                .containsExactly(activity.getName(), activity.getStartDate(), activity.getEndDate(), activity.getCode());


    }

    @Test
    @DisplayName("Should map activity DTO to activity entity")
    public void shouldMapActivityDTOToActivityEntity() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        ActivityDTO activityDTO = new ActivityDTO("Activity1", now.plusHours(1), now.plusHours(3), "Code1");
        // When
        Activity activity = activityMapper.mapDTOToEntity(activityDTO);
        // Then
        assertThat(activity).isNotNull();
        assertThat(activity)
                .extracting("name", "startDate", "endDate", "code")
                .containsExactly(activityDTO.name(), activityDTO.startDate(), activityDTO.endDate(), activityDTO.code());
    }
}
