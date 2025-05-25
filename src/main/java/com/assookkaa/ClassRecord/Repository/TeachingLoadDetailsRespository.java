package com.assookkaa.ClassRecord.Repository;

import com.assookkaa.ClassRecord.Entity.TeachingLoadDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeachingLoadDetailsRespository extends JpaRepository<TeachingLoadDetails, Integer> {

    Optional <TeachingLoadDetails> findByHashKey(String hashKey);
    List <TeachingLoadDetails> findByEnrollmentsId (Integer enrollmentsId);
    List <TeachingLoadDetails> findByEnrollmentsIdIn (List<Integer> enrollmentsId);
    List <TeachingLoadDetails> findByTeachingLoadId (Integer teachingLoadId);
}
