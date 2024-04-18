package com.upwork.challenge.service;

import com.upwork.challenge.dto.ActivityDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ActivityService {

    List<ActivityDTO> getActivitiesByClassroom(String qrCode);

    List<ActivityDTO> checkInToActivity(String username, ActivityDTO activityDTO);
}
