package com.assookkaa.ClassRecord.Repository;

import com.assookkaa.ClassRecord.Dto.Response.Grading.Category.GradesPerCategory;
import com.assookkaa.ClassRecord.Entity.Grading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface GradingRepository extends JpaRepository<Grading, Integer> {
    List <Grading> findByTeachingLoadDetailsIdAndTermId(Integer teachingLoadDetailId, Integer termId);
    List <Grading> findByTeachingLoadDetailsIdAndTermIdAndCategoryId(Integer teachingLoadDetailId, Integer termId, Integer categoryId);
    Grading findByGradingDetailsId(Integer gradingDetailsId);

}
