package com.upwork.challenge.controller;

import com.upwork.challenge.dto.ActivityDTO;
import com.upwork.challenge.service.ActivityService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class ClassAttendanceControllerImpl implements ClassAttendanceController {

    private final ActivityService activityService;


    public ClassAttendanceControllerImpl (ActivityService activityService) {
        this.activityService = activityService;
    }

    @Override
    public List<ActivityDTO> getActivitiesByClassroom(@PathVariable String qrCode) {
        return activityService.getActivitiesByClassroom(qrCode);
    }

    @Override
    public List<ActivityDTO> checkInToActivity(@RequestBody ActivityDTO activityDTO, Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return activityService.checkInToActivity(principal.getUsername(), activityDTO);
    }
}
