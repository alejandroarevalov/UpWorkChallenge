package com.upwork.challenge.factory;

import com.upwork.challenge.controller.ClassAttendanceController;
import com.upwork.challenge.controller.ClassAttendanceControllerImpl;
import com.upwork.challenge.controller.LoginController;
import com.upwork.challenge.controller.LoginControllerImpl;
import com.upwork.challenge.domain.Activity;
import com.upwork.challenge.dto.ActivityDTO;
import com.upwork.challenge.errorhandling.UpworkStudentsExceptionHandler;
import com.upwork.challenge.mapper.ActivityMapper;
import com.upwork.challenge.mapper.EntityDTOMapper;
import com.upwork.challenge.repository.ActivityRepository;
import com.upwork.challenge.repository.ClassroomRepository;
import com.upwork.challenge.repository.UserRepository;
import com.upwork.challenge.security.JwtAuthFilter;
import com.upwork.challenge.service.ActivityService;
import com.upwork.challenge.service.ActivityServiceImpl;
import com.upwork.challenge.service.JwtService;
import com.upwork.challenge.service.JwtServiceImpl;
import com.upwork.challenge.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class BeanFactory {

    @Bean
    public ClassAttendanceController classAttendanceController(ActivityService activityService) {
        return new ClassAttendanceControllerImpl(activityService);
    }

    @Bean
    public LoginController loginController(AuthenticationManager authenticationManager, JwtService jwtService) {
        return new LoginControllerImpl(authenticationManager, jwtService);
    }

    @Bean
    public ActivityService activitiesService(ActivityRepository activityRepository,
                                             ClassroomRepository classroomRepository,
                                             EntityDTOMapper<Activity, ActivityDTO> activityMapper,
                                             UserRepository userRepository) {
        return new ActivityServiceImpl(activityRepository, classroomRepository,
                userRepository, activityMapper);
    }

    @Bean
    public EntityDTOMapper<Activity, ActivityDTO> activitiesMapper() {
        return new ActivityMapper();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }

    @Bean
    public JwtService jwtService() {
        return new JwtServiceImpl();
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        return new JwtAuthFilter(jwtService, userDetailsService);
    }

    @Bean
    public UpworkStudentsExceptionHandler upworkStudentsExceptionHandler() {
        return new UpworkStudentsExceptionHandler();
    }
}
