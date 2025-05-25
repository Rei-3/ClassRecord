package com.assookkaa.ClassRecord.Repository;

import com.assookkaa.ClassRecord.Dto.Response.Grading.Category.GradesPerCategory;
import com.assookkaa.ClassRecord.Entity.Enrollments;
import com.assookkaa.ClassRecord.Entity.Grading;
import com.assookkaa.ClassRecord.Entity.GradingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface GradingDetailRepository extends JpaRepository<GradingDetail, Integer> {
    List<GradingDetail> findByGradingId(Integer grading_id);
    Optional<GradingDetail> findByGradingIdAndEnrollmentsId(Integer gradingId, Integer enrollmentId);
//    List<GradingDetail> findAllByGradingIdAndEnrollmentsId(Integer gradingId, Integer enrollmentId);
    List <GradingDetail> findByEnrollments_IdIn(List<Integer> enrollments_id);
  List <GradingDetail> findByEnrollmentsId(Integer enrollmentId);
//    @Query("SELECT gd from GradingDetail  gd LEFT JOIN  FETCH  gd.grading g WHERE  gd.enrollments.id = :enrollmentId")
//    List <GradingDetail> findByEnrollmentWithGrading(Integer enrollmentId);

    @Query("""
   SELECT new com.assookkaa.ClassRecord.Dto.Response.Grading.Category.GradesPerCategory
   (
    g.id,
    gd.id,
    e.id,
g.description,
g.dateConducted,
g.numberOfItems,
gd.score,
  gd.recordedOn
)
 FROM Grading g
LEFT JOIN g.gradingDetails gd
LEFT JOIN gd.enrollments e
WHERE g.term.id = :termId
AND g.category.id = :categoryId
AND g.teachingLoadDetails.id = :teachingLoadDetailId
""")
    List<GradesPerCategory> findGradesPerCategory( @Param("teachingLoadDetailId") Integer teachingLoadDetailId,
                                                   @Param("termId") Integer termId,
                                                   @Param("categoryId") Integer categoryId);

     @Query("""
   SELECT new com.assookkaa.ClassRecord.Dto.Response.Grading.Category.GradesPerCategory
   (   
    g.id,
    gd.id,
    e.id,
g.description,
g.dateConducted,
g.numberOfItems,
gd.score,
  gd.recordedOn
)
 FROM Grading g
LEFT JOIN g.gradingDetails gd WITH gd.enrollments.id = :enrollmentId
LEFT JOIN gd.enrollments e
WHERE g.term.id = :termId
AND g.category.id = :categoryId
AND g.teachingLoadDetails.id = :teachingLoadDetailId
""")
    List<GradesPerCategory> findGradesPerCategoryAndStudent( @Param("teachingLoadDetailId") Integer teachingLoadDetailId, 
                                                             @Param("termId") Integer termId, 
                                                             @Param("categoryId") Integer categoryId, 
                                                             @Param("enrollmentId") Integer enrollmentId);

}
