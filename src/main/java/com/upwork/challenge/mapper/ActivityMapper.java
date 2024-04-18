package com.upwork.challenge.mapper;

import com.upwork.challenge.domain.Activity;
import com.upwork.challenge.dto.ActivityDTO;

public class ActivityMapper implements EntityDTOMapper<Activity, ActivityDTO> {

    @Override
    public ActivityDTO mapEntityToDTO(Activity activity) {
        return new ActivityDTO(
                activity.getName(),
                activity.getStartDate(),
                activity.getEndDate(),
                activity.getCode());
    }

    @Override
    public Activity mapDTOToEntity(ActivityDTO activityDTO) {
        Activity activity = new Activity();
        activity.setName(activityDTO.name());
        activity.setStartDate(activityDTO.startDate());
        activity.setEndDate(activityDTO.endDate());
        activity.setCode(activityDTO.code());
        return activity;
    }
}
