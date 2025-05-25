package com.assookkaa.ClassRecord.Repository;

import com.assookkaa.ClassRecord.Entity.GradeBase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeBaseRepository extends JpaRepository<GradeBase, Integer> {

    GradeBase findByTeachingLoadDetailsId(Integer teachingLoadDetailsId);

}
