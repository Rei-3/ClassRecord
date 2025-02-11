package com.assookkaa.ClassRecord.Repository;


import com.assookkaa.ClassRecord.Entity.College;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollegeRepository extends JpaRepository<College, Integer> {
}
