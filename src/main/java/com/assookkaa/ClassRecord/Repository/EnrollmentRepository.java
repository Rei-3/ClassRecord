package com.assookkaa.ClassRecord.Repository;

import com.assookkaa.ClassRecord.Entity.Enrollments;
import com.assookkaa.ClassRecord.Entity.Students;
import com.assookkaa.ClassRecord.Entity.TeachingLoadDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollments, Integer> {
    Boolean existsByStudentAndTeachingLoadDetail(Students students, TeachingLoadDetails teachingLoadDetails);
    List <Enrollments> findByTeachingLoadDetailId(Integer teachingLoadDetailId);

    List <Enrollments> findAllByStudentId(Integer studentId);
    Enrollments findByStudentIdAndTeachingLoadDetailId(Integer studentId, Integer teachingLoadDetailId);

    @Query("""
    SELECT e FROM Enrollments e
    JOIN e.student s
    JOIN e.teachingLoadDetail t
    WHERE s.studentId =:studentId
    AND t.id =:teachingLoadDetailId
""")
    Enrollments faFindByStudentIdAndTeachingLoadDetailId(
            @Param("studentId") Integer studentId,
            @Param("teachingLoadDetailId") Integer teachingLoadDetailId
    );
}
