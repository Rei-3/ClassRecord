package com.assookkaa.ClassRecord.Repository;


import com.assookkaa.ClassRecord.Entity.TeachingLoad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeachingLoadRepository extends JpaRepository<TeachingLoad, Integer> {
    List<TeachingLoad> findByTeachersId(Integer teacherId);

    TeachingLoad findByTeachers_Id(Integer teacherId);

}
