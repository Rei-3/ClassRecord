package com.assookkaa.ClassRecord.Service.Attendance;

import com.assookkaa.ClassRecord.Dto.Request.Attendance.AttendanceRecordRequest;
import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingDetailRequest;
import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingRequest;
import com.assookkaa.ClassRecord.Dto.Response.Attendance.AttendanceResponse;
import com.assookkaa.ClassRecord.Dto.Response.Grading.GradingResponse;
import com.assookkaa.ClassRecord.Entity.*;

import com.assookkaa.ClassRecord.Repository.EnrollmentRepository;
import com.assookkaa.ClassRecord.Repository.GradingDetailRepository;
import com.assookkaa.ClassRecord.Repository.GradingRepository;
import com.assookkaa.ClassRecord.Repository.TeachingLoadDetailsRespository;
import com.assookkaa.ClassRecord.Service.Attendance.Interface.AttendanceInterface;

import com.assookkaa.ClassRecord.Utils.Objects.Attendance.AttendanceFunc;
import com.assookkaa.ClassRecord.Utils.Objects.Grading.GradingFunc;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class AttendanceService implements AttendanceInterface {

    private final AttendanceFunc attendanceFunc;
    private final TeachingLoadDetailsRespository teachingLoadDetailsRespository;
    private final GradingRepository gradingRepository;
    private final GradingFunc gradingFunc;
    private final EnrollmentRepository enrollmentRepository;
    private final GradingDetailRepository gradingDetailRepository;

    public AttendanceService(AttendanceFunc attendanceFunc, TeachingLoadDetailsRespository teachingLoadDetailsRespository, GradingRepository gradingRepository, GradingFunc gradingFunc, EnrollmentRepository enrollmentRepository, GradingDetailRepository gradingDetailRepository) {
        this.attendanceFunc = attendanceFunc;
        this.teachingLoadDetailsRespository = teachingLoadDetailsRespository;
        this.gradingRepository = gradingRepository;
        this.gradingFunc = gradingFunc;
        this.enrollmentRepository = enrollmentRepository;
        this.gradingDetailRepository = gradingDetailRepository;
    }

    @Override
    @Transactional
    public GradingResponse buildAttendance(GradingRequest request) {
        TeachingLoadDetails teachingLoadDetails = gradingFunc.findTeachingLoadDetailId(request.getTeachingLoadDetailId());
        GradeCategory category = gradingFunc.findGradeCategory(1);
        Term term = gradingFunc.findTermById(request.getTermId());

        Grading grading = gradingFunc.buildActivity(request,teachingLoadDetails, category,term);

        gradingFunc.batchGradingResponse(grading);
        gradingRepository.save(grading);

        List <Enrollments> enrollments = enrollmentRepository.findByTeachingLoadDetailId(request.getTeachingLoadDetailId());
        List <GradingDetail> gradingDetails = new ArrayList<>();

        for (Enrollments enrollment: enrollments) {
          GradingDetail gradingDetail = gradingFunc.inputStudentInTheScoreSheetRecord(
                  new GradingDetailRequest(), grading, enrollment);
          gradingDetails.add(gradingDetail);

        }
        gradingDetailRepository.saveAll(gradingDetails);
        return gradingFunc.batchGradingResponse(grading);
    }

    @Override
    @Transactional
    public AttendanceResponse markAsPresent(AttendanceRecordRequest request) {
        return attendanceFunc.recordPresent(request);
    }

}