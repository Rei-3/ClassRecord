package com.assookkaa.ClassRecord.Utils.Objects;

import com.assookkaa.ClassRecord.Entity.*;
import com.assookkaa.ClassRecord.Repository.*;
import com.assookkaa.ClassRecord.Utils.ApiException;
import com.assookkaa.ClassRecord.Utils.Objects.Interface.SuperInterface;

public class Super implements SuperInterface {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final SubjectsRepository subjectsRepository;
    private final SemRepository semRepository;
    private final GradeCategoryRepository gradeCategoryRepository;
    private final TeachingLoadDetailsRespository teachingLoadDetailsRespository;

    public Super(TeacherRepository teacherRepository, StudentRepository studentRepository, SubjectsRepository subjectsRepository, SemRepository semRepository, GradeCategoryRepository gradeCategoryRepository, TeachingLoadDetailsRespository teachingLoadDetailsRespository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.subjectsRepository = subjectsRepository;
        this.semRepository = semRepository;
        this.gradeCategoryRepository = gradeCategoryRepository;
        this.teachingLoadDetailsRespository = teachingLoadDetailsRespository;
    }

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
}
