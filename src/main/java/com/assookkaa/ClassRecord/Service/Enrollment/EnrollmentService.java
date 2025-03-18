package com.assookkaa.ClassRecord.Service.Enrollment;

import com.assookkaa.ClassRecord.Config.Filter.JwtUtil;
import com.assookkaa.ClassRecord.Dto.Request.Enrollment.EnrollmentRequestDto;
import com.assookkaa.ClassRecord.Dto.Response.Enrollment.EnrollmentResponseDto;
import com.assookkaa.ClassRecord.Entity.Courses;
import com.assookkaa.ClassRecord.Entity.Enrollments;
import com.assookkaa.ClassRecord.Entity.Students;
import com.assookkaa.ClassRecord.Entity.TeachingLoadDetails;
import com.assookkaa.ClassRecord.Repository.CoursesRepository;
import com.assookkaa.ClassRecord.Repository.EnrollmentRepository;
import com.assookkaa.ClassRecord.Repository.StudentRepository;
import com.assookkaa.ClassRecord.Repository.TeachingLoadDetailsRespository;
import com.assookkaa.ClassRecord.Service.Enrollment.Interface.EnrollmentInterface;
import com.assookkaa.ClassRecord.Utils.ApiException;
import com.assookkaa.ClassRecord.Utils.Objects.Enrollments.EnrollmentsFunc;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EnrollmentService implements EnrollmentInterface {

    private final JwtUtil jwtUtil;
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentsFunc enrollmentsFunc;

    public EnrollmentService(JwtUtil jwtUtil,
                             EnrollmentRepository enrollmentRepository,
                             EnrollmentsFunc enrollmentsFunc
                             ) {
        this.jwtUtil = jwtUtil;
        this.enrollmentRepository = enrollmentRepository;
        this.enrollmentsFunc = enrollmentsFunc;
    }


    @Override
    public EnrollmentResponseDto addEnrollment(String token, EnrollmentRequestDto enrollmentRequestDto) {
        String username = jwtUtil.getUsernameFromToken(token);
        Students students = enrollmentsFunc.findStudentByUsername(username);

        TeachingLoadDetails teachingLoadDetails = enrollmentsFunc.findTeachingLoadDetailByHashKey(enrollmentRequestDto.getHashKey());

        if(enrollmentsFunc.isStudentAlreadyEnrolled(students, teachingLoadDetails)) {
            throw new ApiException("Yoooo ur already in foo", 69, "Enrolled No Cap");
        }

        Enrollments enrollmentSave= enrollmentsFunc.buildEnrollment(enrollmentRequestDto, students, teachingLoadDetails);
        enrollmentRepository.save(enrollmentSave);


        return enrollmentsFunc.mapToEnrollmentResponse(enrollmentSave);
    }

}
