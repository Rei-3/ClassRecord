package com.assookkaa.ClassRecord.Repository;

import com.assookkaa.ClassRecord.Entity.Sem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemRepository extends JpaRepository<Sem, Integer> {
}
