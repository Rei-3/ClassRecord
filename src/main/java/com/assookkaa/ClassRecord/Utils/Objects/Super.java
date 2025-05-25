package com.assookkaa.ClassRecord.Utils.Objects;

import com.assookkaa.ClassRecord.Entity.*;
import com.assookkaa.ClassRecord.Repository.*;
import com.assookkaa.ClassRecord.Utils.ApiException;
import com.assookkaa.ClassRecord.Utils.Interface.SuperInterface;
import com.assookkaa.ClassRecord.Utils.Token.TokenDecryption;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Component
@AllArgsConstructor
public class Super implements SuperInterface {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final SubjectsRepository subjectsRepository;
    private final SemRepository semRepository;
    private final GradeCategoryRepository gradeCategoryRepository;
    private final TeachingLoadDetailsRespository teachingLoadDetailsRespository;
    private final TermRepository termRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final TokenDecryption tokenDecryption;

    @Override
    public Teachers findTeacherByUsername(String username) {
        Teachers teachers = teacherRepository.findTeacherByUsername(username);
        if(teachers == null){
            throw new ApiException("Teacher Not Found", 404, "TEACHER_USER_NOT_FOUND");
        }
        return teachers;
    }

    @Override
    public Students findStudentByUsername(String username) {

      return studentRepository.findByUsername(username).orElseThrow(
              () -> new ApiException("Student Not Found", 404, "STUDENT_NOT_FOUND")
      );

    }

    @Override
    public Subjects findSubjectbyId(Integer subjectId) {
        return subjectsRepository.findById(subjectId).orElseThrow(
                () -> new ApiException("Subject Not Found", 404, "SUBJECT_NOT_FOUND")
        );
    }

    @Override
    public Sem findSembyId(Integer semId) {
        return semRepository.findById(semId).orElseThrow(
                () -> new ApiException("Sem Not Found", 404, "SEM_NOT_FOUND")
        );
    }

    @Override
    public GradeCategory findGradeCategory(Integer id) {
        return gradeCategoryRepository.findById(id).orElseThrow(()->
                new ApiException("Grading Category Not Found", 404, "DETAILS_NOT_FOND"));
    }

    @Override
    public TeachingLoadDetails findTeachingLoadDetailId(Integer id) {
        return teachingLoadDetailsRespository.findById(id).orElseThrow(()->
                new ApiException("Teaching Load Details Not Found", 404, "DETAILS_NOT_FOND"));
    }

    @Override
    public TeachingLoadDetails findTeachingLoadDetailByHashKey(String hashKey) {
        return teachingLoadDetailsRespository.findByHashKey(hashKey).orElseThrow(()->
                new RuntimeException("Subject Not Found"));
    }

    @Override
    public Term findTermById(Integer id) {
        return termRepository.findById(id).orElseThrow(()->
                new RuntimeException("Term Not Found"));
    }

    @Override
    public void checkKeys(String API_KEY, String SECRET_KEY) {
       if(API_KEY == null || SECRET_KEY == null){
           throw new RuntimeException("API Key or Secret Key Not Found");
       }

       if(!MessageDigest.isEqual(
               tokenDecryption.getApiKey().getBytes(StandardCharsets.UTF_8),
               API_KEY.getBytes(StandardCharsets.UTF_8)
       )|| !MessageDigest.isEqual(
               tokenDecryption.getSecretKey().getBytes(StandardCharsets.UTF_8),
               SECRET_KEY.getBytes(StandardCharsets.UTF_8)
       )){
           throw new IllegalArgumentException("Wrong Key Bozo");
       }
    }


}
