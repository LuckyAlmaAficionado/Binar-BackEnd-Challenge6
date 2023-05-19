package com.binar.securityspringboot.repository;

import com.binar.securityspringboot.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Optional<Schedule> findByMovieFk(Long movieFk);

    Schedule findByScheduleId(Long scheduleId);
}
