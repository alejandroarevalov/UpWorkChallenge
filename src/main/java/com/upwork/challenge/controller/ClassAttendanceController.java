package com.upwork.challenge.controller;

import com.upwork.challenge.dto.ActivityDTO;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface ClassAttendanceController {

    @GetMapping("/classroom/{qrCode}/activities")
    List<ActivityDTO> getActivitiesByClassroom(@PathVariable String qrCode);

    @PostMapping("/activities/check-in")
    List<ActivityDTO> checkInToActivity(@RequestBody ActivityDTO activityCode, Authentication authentication);
}
