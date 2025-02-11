package com.assookkaa.ClassRecord.Repository;

import com.assookkaa.ClassRecord.Entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Integer> {
    Roles findByRoleName(String roleName);
}
