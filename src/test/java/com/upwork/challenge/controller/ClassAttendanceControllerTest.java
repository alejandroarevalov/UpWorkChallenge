package com.upwork.challenge.controller;

import com.upwork.challenge.dto.ActivityDTO;
import com.upwork.challenge.service.ActivityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ClassAttendanceControllerTest {

    @InjectMocks
    private ClassAttendanceControllerImpl classAttendanceController;
    @Mock
    private ActivityService activityService;

    @Test
    @DisplayName("Should get a list of activities successfully")
    public void shouldGetAListOfActivitiesSuccessfully() {
        // Given
        String classroomQR = "classroomQRCodeHashed";
        LocalDateTime now = LocalDateTime.now();
        ActivityDTO activityDTO1 = new ActivityDTO("Activity1", now.plusHours(1), now.plusHours(3), "Code1");
        ActivityDTO activityDTO2 = new ActivityDTO("Activity2", now.plusMinutes(30L), now.plusMinutes(150L), "Code2");
        ActivityDTO activityDTO3 = new ActivityDTO("Activity3", now.plusHours(2), now.plusHours(4), "Code3");
        List<ActivityDTO> activitiesDTOList = List.of(activityDTO1, activityDTO2, activityDTO3);
        // When
        when(activityService.getActivitiesByClassroom(classroomQR)).thenReturn(activitiesDTOList);
        List<ActivityDTO> activitiesDTOListResult = classAttendanceController.getActivitiesByClassroom(classroomQR);
        // Then
        assertThat(activitiesDTOList).isNotNull();
        assertThat(activitiesDTOList).hasSize(3);
        verify(activityService).getActivitiesByClassroom(classroomQR);
    }

    @Test
    @DisplayName("Should allow user to check-in to an activity successfully")
    public void shouldAllowUserToCheckInToAnActivitySuccessfully() {
        // Given
        User principal = new User("alejandroarevalov@gmail.com", "123456", Collections.emptyList());
        String activityCode = "ActivityCode";
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                principal,
                principal.getPassword()
        );
        LocalDateTime now = LocalDateTime.now();
        ActivityDTO activityDTO = new ActivityDTO(null, null, null, activityCode);
        // When
        when(activityService.checkInToActivity(principal.getUsername(), activityDTO)).thenReturn(
                List.of(new ActivityDTO("ActivityName", now.plusHours(1), now.plusHours(3), activityCode)));
        List<ActivityDTO> activitiesDTOResultList = classAttendanceController.checkInToActivity(activityDTO, authToken);
        // Then
        assertThat(activitiesDTOResultList).isNotNull();
        assertThat(activitiesDTOResultList).hasSize(1);
        verify(activityService).checkInToActivity(principal.getUsername(), activityDTO);
    }
}
