package com.assookkaa.ClassRecord.Repository;

import com.assookkaa.ClassRecord.Entity.Grading;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface GradingRepository extends JpaRepository<Grading, Integer> {
    List <Grading> findByTeachingLoadDetailsIdAndTermId(Integer teachingLoadDetailId, Integer termId);
    List <Grading>findByTeachingLoadDetailsIdAndTermIdAndCategoryId(Integer teachingLoadDetailId, Integer termId, Integer categoryId);

    Optional<Grading> findTopByTeachingLoadDetailsIdAndCategoryIdOrderByDateConducted(Integer teachingLoadDetailId, Integer categoryId);

    Optional<Grading> findTopByTeachingLoadDetailsIdAndCategoryIdAndTermIdOrderByDateConducted(Integer teachingLoadDetailId, Integer categoryId, Integer termId);

    @Query("SELECT COUNT(g) > 0 FROM Grading g WHERE DATE(g.dateConducted) = DATE(:dateConducted) AND g.category.id = 4 AND g.term.id = :termId")
    boolean existsByDateConducted(
            @Param("dateConducted") Date dateConducted,
            @Param("termId") int termId
            );
//    @Query("SELECT g FROM Grading g WHERE " +
//            "g.teachingLoadDetails.id = :teachingLoadDetailId " +
//            "AND g.category.id = 4 " +
//            "AND g.dateConducted = CURRENT_DATE")
//    Optional<Grading>findTodayAttendanceGrading(@Param("teachingLoadDetailId")Integer teachingLoadDetailId);

    boolean existsByDateConductedAndTermIdAndCategoryIdAndTeachingLoadDetailsId(Date dateConducted, Integer termId, Integer categoryId, Integer teachingLoadDetailId);
}
