package com.assookkaa.ClassRecord.Service.Auth;

import com.assookkaa.ClassRecord.Entity.Roles;
import com.assookkaa.ClassRecord.Repository.RolesRepository;
import com.assookkaa.ClassRecord.Repository.StudentRepository;
import com.assookkaa.ClassRecord.Repository.TeacherRepository;
import com.assookkaa.ClassRecord.Repository.UserRepository;
import com.assookkaa.ClassRecord.Utils.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ValidationService {
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final RolesRepository rolesRepository;
    private final StudentRepository studentRepository;

    public void TeacherValidationChecker (String email, String teacherId){
        List<String> errors = new ArrayList<>();
        if(userRepository.existsByEmail(email)) {
            errors.add("Email is already in use");
        }
        if (teacherRepository.existsByTeacherId(teacherId)) {
            errors.add("Teacher id is already in use");
        }
        if(!errors.isEmpty()) {
            throw new ApiException(String.join(", ", errors), 409, "CREDENTIAL_ALREADY_EXISTS");
        }
    }

    public void StudentValidationChecker (String email, Integer studentId){
        List<String> errors = new ArrayList<>();
        if(userRepository.existsByEmail(email)) {
            errors.add("Email is already in use");

        }
        if (studentRepository.existsByStudentId(studentId)) {
            errors.add("Student id is already in use");

        }
        if(!errors.isEmpty()) {
            throw new ApiException(String.join(", ", errors), 409, "CREDENTIAL_ALREADY_EXISTS");
        }
    }

    public void UsernameChecker(String username){
        if (userRepository.existsByUsername(username)){
            throw new ApiException("Username is already in use", 409, "USERNAME_EXISTS");
        };
    }

    public void RoleChecker (Roles role){
        if(role == null) {
            throw new ApiException("Role not found", 404, "ROLE_NOT_FOUND");
        }
    }
}
