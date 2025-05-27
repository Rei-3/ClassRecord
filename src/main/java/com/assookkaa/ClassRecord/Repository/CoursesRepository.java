package com.assookkaa.ClassRecord.Repository;

import com.assookkaa.ClassRecord.Entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursesRepository extends JpaRepository <Courses, Integer>{
    Courses findByStudentsId(Integer studentsId);
    Courses findBySubjectsId(Integer subjectsId);
}
