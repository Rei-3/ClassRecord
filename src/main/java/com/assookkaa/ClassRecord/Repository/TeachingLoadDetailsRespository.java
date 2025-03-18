package com.assookkaa.ClassRecord.Repository;

import com.assookkaa.ClassRecord.Entity.TeachingLoadDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeachingLoadDetailsRespository extends JpaRepository<TeachingLoadDetails, Integer> {

    Optional <TeachingLoadDetails> findByHashKey(String hashKey);
}
