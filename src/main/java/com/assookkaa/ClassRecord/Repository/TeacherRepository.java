package com.assookkaa.ClassRecord.Repository;

import com.assookkaa.ClassRecord.Dto.Response.User.TeacherUser;
import com.assookkaa.ClassRecord.Entity.Teachers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeacherRepository extends JpaRepository<Teachers, Integer> {
    Boolean existsByTeacherId(String teacher_id);

    @Query("SELECT t FROM Teachers t JOIN t.teacher u WHERE u.username = :username")
    Teachers findTeacherByUsername(@Param("username") String username);

}
