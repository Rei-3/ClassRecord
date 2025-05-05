package com.assookkaa.ClassRecord.Utils.Objects.Attendance;

import com.assookkaa.ClassRecord.Dto.Request.Attendance.AttendanceBatchBuildRequest;
import com.assookkaa.ClassRecord.Dto.Request.Attendance.AttendanceRecordRequest;
import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingRequest;
import com.assookkaa.ClassRecord.Dto.Response.Attendance.AttendanceResponse;
import com.assookkaa.ClassRecord.Entity.*;
import com.assookkaa.ClassRecord.Repository.*;
import com.assookkaa.ClassRecord.Utils.Interface.Attendance.AttendanceFuncInterface;
import com.assookkaa.ClassRecord.Utils.Objects.Super;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
public class AttendanceFunc extends Super implements AttendanceFuncInterface {


    private final EnrollmentRepository enrollmentRepository;
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final GradingRepository gradingRepository;
    private final GradingDetailRepository gradingDetailRepository;
    private final TeachingLoadDetailsRespository teachingLoadDetailsRespository;

    public AttendanceFunc(TeacherRepository teacherRepository,
                          SubjectsRepository subjectsRepository,
                          SemRepository semRepository,
                          GradeCategoryRepository gradeCategoryRepository,
                          TeachingLoadDetailsRespository teachingLoadDetailsRespository,
                          TermRepository termRepository,
                          EnrollmentRepository enrollmentRepository, AttendanceRepository attendanceRepository, StudentRepository studentRepository, GradingRepository gradingRepository, GradingDetailRepository gradingDetailRepository) {
        super(teacherRepository, studentRepository, subjectsRepository, semRepository, gradeCategoryRepository, teachingLoadDetailsRespository, termRepository, enrollmentRepository);
        this.enrollmentRepository = enrollmentRepository;
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.gradingRepository = gradingRepository;
        this.gradingDetailRepository = gradingDetailRepository;
        this.teachingLoadDetailsRespository = teachingLoadDetailsRespository;
    }

    @Override
    public Attendance buildActivity(AttendanceRecordRequest attendanceRecordRequest, Enrollments enrollments) {
        return Attendance.builder()
                .enrollments(enrollments)
                .isPresent(false)
                .date(LocalDateTime.now())
                .build();
    }
    public Grading buildActivity(AttendanceBatchBuildRequest dto,
                                 TeachingLoadDetails teachingLoadDetails,
                                 GradeCategory category,
                                 Term term)
    {
        return Grading.builder()
                .teachingLoadDetails(teachingLoadDetails)
                .description("Attendance" + new Date())
                .numberOfItems(1)
                .category(category)
                .term(term)
                .dateConducted(new Date())
                .build();
    }

    @Override
    public List<Enrollments> findTeachingLoadIdEnrollment(Integer teachingLoadId) {
        return enrollmentRepository.findByTeachingLoadDetailId(teachingLoadId);
    }

    @Override
    public void saveAll(List<Attendance> attendances) {
        attendanceRepository.saveAll(attendances);
    }

    @Override
    public AttendanceResponse recordPresent(AttendanceRecordRequest dto) {
        return null;
    }


}
