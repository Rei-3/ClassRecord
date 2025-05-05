package com.assookkaa.ClassRecord.Repository;

import com.assookkaa.ClassRecord.Entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AttendanceRepository  extends JpaRepository<Attendance, Integer> {
    Optional<Attendance> findTopByEnrollmentsIdOrderByDateDesc(Integer enrollmentsId);

}
