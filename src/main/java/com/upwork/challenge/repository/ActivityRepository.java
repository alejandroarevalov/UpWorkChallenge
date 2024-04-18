package com.upwork.challenge.repository;

import com.upwork.challenge.domain.Activity;
import com.upwork.challenge.domain.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findByClassroom(Classroom classroom);

    Optional<Activity> findByCode(String code);
}
