package com.assookkaa.ClassRecord.Repository;

import com.assookkaa.ClassRecord.Entity.GradingDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GradingDetailRepository extends JpaRepository<GradingDetail, Integer> {
    List<GradingDetail> findByGradingId(Integer gradingId);
    Optional<GradingDetail> findByGradingIdAndEnrollmentsId(Integer gradingId, Integer enrollmentId);
}
