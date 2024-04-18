package com.upwork.challenge.repository;

import com.upwork.challenge.domain.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    Optional<Classroom> findByQrCode(String qrCode);
}
