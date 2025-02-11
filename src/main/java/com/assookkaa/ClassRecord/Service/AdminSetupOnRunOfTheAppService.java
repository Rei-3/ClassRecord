package com.assookkaa.ClassRecord.Service;

import com.assookkaa.ClassRecord.Entity.Roles;
import com.assookkaa.ClassRecord.Entity.User;
import com.assookkaa.ClassRecord.Repository.RolesRepository;
import com.assookkaa.ClassRecord.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class AdminSetupOnRunOfTheAppService {


    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSetupOnRunOfTheAppService(UserRepository userRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void SummonAdmin(){

        Roles admin = rolesRepository.getReferenceById(1);
        User adminUser = userRepository.findByUsername(null);
        if(adminUser == null) {
            LocalDate dob = LocalDate.of(2002, 11, 24);
            adminUser = new User();
            adminUser.setFname("Asuka");
            adminUser.setMname("Langley");
            adminUser.setLname("Soryu");
            adminUser.setGender(true);
            adminUser.setOtp(null);
            adminUser.setEmail("dbeast415@gmail.com");
            adminUser.setPassword(passwordEncoder.encode("12345678"));
            adminUser.setUsername("Assookkaa");
            adminUser.setDob(dob);
            adminUser.setRole(admin);

            userRepository.save(adminUser);
        }
    }

}
