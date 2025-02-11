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
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EnrollmentService implements EnrollmentInterface {

    private final CoursesRepository coursesRepository;
    private final TeachingLoadDetailsRespository teachingLoadDetailsRespository;
    private final JwtUtil jwtUtil;
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(CoursesRepository coursesRepository, TeachingLoadDetailsRespository teachingLoadDetailsRespository, JwtUtil jwtUtil, StudentRepository studentRepository, EnrollmentRepository enrollmentRepository) {
        this.coursesRepository = coursesRepository;
        this.teachingLoadDetailsRespository = teachingLoadDetailsRespository;
        this.jwtUtil = jwtUtil;
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public EnrollmentResponseDto addEnrollment(String token, EnrollmentRequestDto enrollmentRequestDto) {
        String username = jwtUtil.getUsernameFromToken(token);
        Students student = findStudentById(username);

        TeachingLoadDetails teachingLoadDetails = findTeachingLoadDetailById(enrollmentRequestDto.getTeachingLoadDetailId());
        Courses course = findCourseById(enrollmentRequestDto.getCourseId());

        Enrollments enrollments = buildEnrollment(enrollmentRequestDto, student, teachingLoadDetails, course);
        enrollmentRepository.save(enrollments);
        
        return mapToEnrollmentResponse(enrollments);
    }

    private Students findStudentById(String username) {
        return studentRepository.findByUsername(username).orElseThrow(
                ()-> new ApiException("You are not Sigma", 404, "USER_NOT_FOUND")
        );
    }


    private TeachingLoadDetails findTeachingLoadDetailById (Integer id) {
        return teachingLoadDetailsRespository.findById(id).orElseThrow(
                () -> new ApiException("Its Invalid Sigma try something valid", 404, "DETAIL_NOT_FOUND")
        );
    }

    private Courses findCourseById (Integer id) {
        return coursesRepository.findById(id).orElseThrow(
                () -> new ApiException("What the Skibidi Course got fanum taxed", 404, "COURSE_NOT_FOUND")
        );
    }

    private Enrollments buildEnrollment (EnrollmentRequestDto enrollmentRequestDto,
                                         Students students,
                                         TeachingLoadDetails teachingLoadDetails,
                                         Courses courses) {
        return Enrollments.builder()
                .added_on(new Date())
                .teachingLoadDetail(teachingLoadDetails)
                .student(students)
                .build();
    }

    private EnrollmentResponseDto mapToEnrollmentResponse (Enrollments enrollments) {
        return EnrollmentResponseDto.builder()
                .id(enrollments.getId())
                .studentId(enrollments.getStudent().getId())
                .teachingLoadDetailId(enrollments.getTeachingLoadDetail().getId())
                .enrollmentDate(enrollments.getAdded_on())
                .build();
    }
}
