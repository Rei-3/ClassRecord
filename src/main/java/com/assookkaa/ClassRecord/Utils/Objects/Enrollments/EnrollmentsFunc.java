package com.assookkaa.ClassRecord.Utils.Objects.Enrollments;

import com.assookkaa.ClassRecord.Dto.Request.Enrollment.EnrollmentRequestDto;
import com.assookkaa.ClassRecord.Dto.Response.Enrollment.EnrollmentResponseDto;
import com.assookkaa.ClassRecord.Entity.Enrollments;
import com.assookkaa.ClassRecord.Entity.Students;
import com.assookkaa.ClassRecord.Entity.TeachingLoadDetails;
import com.assookkaa.ClassRecord.Repository.*;
import com.assookkaa.ClassRecord.Utils.Interface.Enrollments.EnrollmentsFuncInterface;
import com.assookkaa.ClassRecord.Utils.Objects.Super;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class EnrollmentsFunc extends Super implements EnrollmentsFuncInterface {
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentsFunc(TeacherRepository teacherRepository, StudentRepository studentRepository, SubjectsRepository subjectsRepository, SemRepository semRepository, GradeCategoryRepository gradeCategoryRepository, TeachingLoadDetailsRespository teachingLoadDetailsRespository, TermRepository termRepository, EnrollmentRepository enrollmentRepository) {
        super(teacherRepository, studentRepository, subjectsRepository, semRepository, gradeCategoryRepository, teachingLoadDetailsRespository, termRepository);
        this.enrollmentRepository = enrollmentRepository;
    }


    @Override
    public Enrollments buildEnrollment(EnrollmentRequestDto enrollmentRequestDto, Students students, TeachingLoadDetails teachingLoadDetails) {
        return Enrollments.builder()
                .added_on(new Date())
                .teachingLoadDetail(teachingLoadDetails)
                .student(students)
                .build();
    }

    @Override
    public EnrollmentResponseDto mapToEnrollmentResponse(Enrollments enrollments) {
        return EnrollmentResponseDto.builder()
                .id(enrollments.getId())
                .studentId(enrollments.getStudent().getId())
                .teachingLoadDetailId(enrollments.getTeachingLoadDetail().getId())
                .enrollmentDate(enrollments.getAdded_on())
                .build();
    }

    @Override
    public Boolean isStudentAlreadyEnrolled(Students students, TeachingLoadDetails teachingLoadDetails) {
        return enrollmentRepository.existsByStudentAndTeachingLoadDetail(students, teachingLoadDetails);
    }


}
