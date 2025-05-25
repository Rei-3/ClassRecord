package com.assookkaa.ClassRecord.Repository;

import com.assookkaa.ClassRecord.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    User findByEmail(String email);
    User findByOtp(String otp);
    List <User> findAllByRoleId(int roleId);


}
