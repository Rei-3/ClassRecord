package com.assookkaa.ClassRecord.Repository;

import com.assookkaa.ClassRecord.Entity.Grading;
import com.assookkaa.ClassRecord.Entity.GradingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GradingDetailRepository extends JpaRepository<GradingDetail, Integer> {
    List<GradingDetail> findByGradingId(Integer grading_id);
    Optional<GradingDetail> findByGradingIdAndEnrollmentsId(Integer gradingId, Integer enrollmentId);
    List<GradingDetail> findByGradingIdInAndEnrollmentsIdIn(List<Integer> gradingIds, List<Integer> enrollmentIds);
    List <GradingDetail> findByEnrollments_IdIn(List<Integer> enrollments_id);

    @Query("""
    SELECT gd
    FROM GradingDetail gd
    JOIN gd.enrollments e
    JOIN e.teachingLoadDetail tld
    JOIN gd.grading g
    WHERE tld.id = :teachingLoadDetailId
      AND g.term.id = :termId
      AND g.category.id = :categoryId
""")
    List<GradingDetail> findGradesPerCategory( @Param("teachingLoadDetailId") Integer teachingLoadDetailId,
                                               @Param("termId") Integer termId,
                                               @Param("categoryId") Integer categoryId);
//
}
