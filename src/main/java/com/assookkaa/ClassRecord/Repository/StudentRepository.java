package com.assookkaa.ClassRecord.Repository;

import com.assookkaa.ClassRecord.Entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Students, Integer> {
    Boolean existsByStudentId(Integer student_id);

    @Query("SELECT s FROM Students s JOIN s.student u WHERE u.username = :username")
    Optional<Students> findByUsername(@Param("username") String username);
}
