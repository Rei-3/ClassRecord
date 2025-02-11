package com.assookkaa.ClassRecord.Repository;

import com.assookkaa.ClassRecord.Entity.Enrollments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollments, Integer> {
}
