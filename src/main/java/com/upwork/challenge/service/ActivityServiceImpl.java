package com.upwork.challenge.service;

import com.upwork.challenge.domain.Activity;
import com.upwork.challenge.domain.Classroom;
import com.upwork.challenge.domain.User;
import com.upwork.challenge.dto.ActivityDTO;
import com.upwork.challenge.errorhandling.UpWorkStudentsResourceNotFoundException;
import com.upwork.challenge.mapper.EntityDTOMapper;
import com.upwork.challenge.repository.ActivityRepository;
import com.upwork.challenge.repository.ClassroomRepository;
import com.upwork.challenge.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final ClassroomRepository classroomRepository;
    private final UserRepository userRepository;
    private final EntityDTOMapper<Activity, ActivityDTO> activityMapper;

    public ActivityServiceImpl(ActivityRepository activityRepository,
                               ClassroomRepository classroomRepository,
                               UserRepository userRepository,
                               EntityDTOMapper<Activity, ActivityDTO> activityMapper) {
        this.activityRepository = activityRepository;
        this.classroomRepository = classroomRepository;
        this.activityMapper = activityMapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<ActivityDTO> getActivitiesByClassroom(String qrCode) {
        Classroom classroom = classroomRepository.findByQrCode(qrCode)
                .orElseThrow(() -> new UpWorkStudentsResourceNotFoundException("Classroom not found for QR code: " + qrCode));
        List<Activity> activitiesList = classroom.getActivities();
        return activitiesList.stream()
                .filter(activity -> activity.getStartDate().isAfter(LocalDateTime.now()))
                .map(activityMapper::mapEntityToDTO)
                .toList();
    }

    @Override
    public List<ActivityDTO> checkInToActivity(String username, ActivityDTO activityDTO) {
        String activityCode = activityDTO.code();
        Activity activity = activityRepository.findByCode(activityCode)
                .orElseThrow(() -> new UpWorkStudentsResourceNotFoundException("No activity found for activity code: " + activityCode));
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UpWorkStudentsResourceNotFoundException("No user found for username: " + username));
        user.setActivity(activity);
        userRepository.save(user);
        return user.getCheckedInActivities()
                .stream()
                .map(activityMapper::mapEntityToDTO)
                .toList();
    }
}
