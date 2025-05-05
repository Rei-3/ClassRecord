package com.assookkaa.ClassRecord.Repository;

import com.assookkaa.ClassRecord.Entity.GradingComposition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GradingCompositionRepository extends JpaRepository<GradingComposition, Integer> {
    List <GradingComposition> findByTeachingLoadDetailId(Integer teachingLoadDetailId);
//   Optional <GradingComposition> findFirstByTeachingLoadDetailId(Integer teachingLoadDetailId);
}
