package com.assookkaa.ClassRecord.Repository;

import com.assookkaa.ClassRecord.Entity.Enrollments;
import com.assookkaa.ClassRecord.Entity.Students;
import com.assookkaa.ClassRecord.Entity.TeachingLoadDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollments, Integer> {
    Boolean existsByStudentAndTeachingLoadDetail(Students students, TeachingLoadDetails teachingLoadDetails);
    List<Enrollments> findByTeachingLoadDetailId(Integer teachingLoadDetailId);
}
