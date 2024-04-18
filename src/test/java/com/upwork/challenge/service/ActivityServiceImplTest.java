package com.upwork.challenge.service;

import com.upwork.challenge.domain.Activity;
import com.upwork.challenge.domain.Classroom;
import com.upwork.challenge.domain.User;
import com.upwork.challenge.dto.ActivityDTO;
import com.upwork.challenge.mapper.EntityDTOMapper;
import com.upwork.challenge.repository.ActivityRepository;
import com.upwork.challenge.repository.ClassroomRepository;
import com.upwork.challenge.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceImplTest {

    //Test subject
    @InjectMocks
    private ActivityServiceImpl activityService;
    @Mock
    private ActivityRepository activityRepository;
    @Mock
    private ClassroomRepository classroomRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EntityDTOMapper<Activity, ActivityDTO> activityMapper;

    @Test
    @DisplayName("Should get all activities list by classroom")
    public void shouldGetAllActivitiesListByClassroom() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        String classroomQRCode = "Classroom1QRCode";
        Classroom classroom = new Classroom(1L, classroomQRCode, null);
        Activity activity1 = new Activity(1L, "Activity1", now.plusHours(1), now.plusHours(3),
                "Code1", classroom, null);
        Activity activity2 = new Activity(2L, "Activity2", now.plusMinutes(30L), now.plusMinutes(150L),
                "Code2", classroom, null);
        Activity activity3 = new Activity(3L, "Activity3", now.plusHours(2), now.plusHours(4),
                "Code3", classroom, null);
        List<Activity> activitiesMockedList = List.of(activity1, activity2, activity3);
        classroom.setActivities(activitiesMockedList);
        ActivityDTO activityDTO1 = new ActivityDTO("Activity1", now.plusHours(1), now.plusHours(3), "Code1");
        ActivityDTO activityDTO2 = new ActivityDTO("Activity2", now.plusMinutes(30L), now.plusMinutes(150L), "Code2");
        ActivityDTO activityDTO3 = new ActivityDTO("Activity3", now.plusHours(2), now.plusHours(4), "Code3");

        // When
        when(classroomRepository.findByQrCode(anyString())).thenReturn(Optional.of(classroom));
        when(activityMapper.mapEntityToDTO(activity1)).thenReturn(activityDTO1);
        when(activityMapper.mapEntityToDTO(activity2)).thenReturn(activityDTO2);
        when(activityMapper.mapEntityToDTO(activity3)).thenReturn(activityDTO3);

        List<ActivityDTO> activitiesResponseList = activityService.getActivitiesByClassroom(classroomQRCode);
        // Then
        assertThat(activitiesResponseList).isNotNull();
        assertThat(activitiesResponseList.size()).isEqualTo(3);
        assertThat(activitiesResponseList).containsExactlyInAnyOrder(activitiesResponseList.toArray(ActivityDTO[]::new)
        );
        verify(classroomRepository).findByQrCode(classroomQRCode);
        verify(activityMapper, times(3)).mapEntityToDTO(any(Activity.class));
    }

    @Test
    @DisplayName("Should throw an exception when a classroom is not found")
    public void shouldThrowAnExceptionWhenAClassroomIsNotFound() {
        // Given
        String qrCode = "123";
        // When
        when(classroomRepository.findByQrCode(qrCode)).thenReturn(Optional.empty());
        // Then
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> activityService.getActivitiesByClassroom(qrCode))
                .withMessageMatching("Classroom not found for QR code: " + qrCode);
        verify(classroomRepository).findByQrCode(qrCode);
    }

    @Test
    @DisplayName("Should allow user to check in to activity")
    public void shouldAllowUserToCheckInToActivity() {
        // Given
        String userName = "alejandroarevalov@gmail.com";
        ActivityDTO inputActivityDTO = new ActivityDTO("Cooking 0", null, null, "CKNG0");
        Activity activity = new Activity(1L, "Cooking 0", LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusHours(3), "CKNG0", null, null);
        ActivityDTO activityDTOMapped = new ActivityDTO(activity.getName(), activity.getStartDate(),
                activity.getEndDate(), activity.getCode());
        User user = new User(1L, userName, "123456", List.of(activity));
        //When
        when(activityRepository.findByCode(anyString())).thenReturn(Optional.of(activity));
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.of(user));
        when(activityMapper.mapEntityToDTO(any(Activity.class))).thenReturn(activityDTOMapped);

        List<ActivityDTO> activitiesDTOList = activityService.checkInToActivity(userName, inputActivityDTO);
        // Then
        assertThat(activitiesDTOList).isNotNull();
        assertThat(activitiesDTOList).size().isGreaterThanOrEqualTo(1);
        assertThat(activitiesDTOList).contains(activityDTOMapped);
        verify(activityRepository).findByCode(anyString());
        verify(userRepository).findByUserName(userName);
        verify(activityMapper, atLeastOnce()).mapEntityToDTO(activity);
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Should throw an exception when activity code is not found")
    public void shouldThrowAnExceptionWhenActivityCodeIsNotFound() {
        // Given
        String userName = "alejandroarevalov@gmail.com";
        ActivityDTO inputActivityDTO = new ActivityDTO("Cooking 0", null, null, "CKNG0");

        // When
        when(activityRepository.findByCode(anyString())).thenReturn(Optional.empty());

        // Then
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> activityService.checkInToActivity(userName, inputActivityDTO))
                .withMessageMatching("No activity found for activity code: " + inputActivityDTO.code());
    }

    @Test
    @DisplayName("Should throw an exception when username is not found")
    public void shouldThrowAnExceptionWhenUserNameIsNotFound() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        String userName = "non-existinguser@gmail.com";
        ActivityDTO inputActivityDTO = new ActivityDTO("Cooking 0", null, null, "CKNG0");
        Activity activity = new Activity(1L, inputActivityDTO.name(), now.plusHours(1), now.plusHours(3),
                inputActivityDTO.code(), null, null);

        // When
        when(activityRepository.findByCode(anyString())).thenReturn(Optional.of(activity));
        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());

        // Then
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> activityService.checkInToActivity(userName, inputActivityDTO))
                .withMessageMatching("No user found for username: " + userName);
        verify(activityRepository).findByCode(inputActivityDTO.code());
    }
}
