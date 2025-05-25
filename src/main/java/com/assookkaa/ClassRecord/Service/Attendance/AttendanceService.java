package com.assookkaa.ClassRecord.Service.Attendance;

import com.assookkaa.ClassRecord.Dto.Request.Attendance.AttendanceBuildRequest;
import com.assookkaa.ClassRecord.Dto.Request.Attendance.AttendanceRecord;
import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingRequest;
import com.assookkaa.ClassRecord.Dto.Response.Attendance.AttendanceResponse;
import com.assookkaa.ClassRecord.Dto.Response.Grading.GradingResponse;
import com.assookkaa.ClassRecord.Entity.*;

import com.assookkaa.ClassRecord.Repository.EnrollmentRepository;
import com.assookkaa.ClassRecord.Repository.GradingDetailRepository;
import com.assookkaa.ClassRecord.Repository.GradingRepository;
import com.assookkaa.ClassRecord.Service.Attendance.Interface.AttendanceInterface;

import com.assookkaa.ClassRecord.Utils.Objects.Grading.GradingFunc;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AttendanceService implements AttendanceInterface {



    private final GradingFunc gradingFunc;
    private final GradingRepository gradingRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final GradingDetailRepository gradingDetailRepository;

    public AttendanceService(GradingFunc gradingFunc, GradingRepository gradingRepository, EnrollmentRepository enrollmentRepository, GradingDetailRepository gradingDetailRepository) {
        this.gradingFunc = gradingFunc;
        this.gradingRepository = gradingRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.gradingDetailRepository = gradingDetailRepository;
    }


    @Override
    @Transactional
    public GradingResponse buildAttendance(AttendanceBuildRequest request) {
        TeachingLoadDetails teachingLoadDetails = gradingFunc.findTeachingLoadDetailId(request.getTeachingLoadDetailId());
        GradeCategory category = gradingFunc.findGradeCategory(4);
        Term term = gradingFunc.findTermById(request.getTermId());

        LocalDate now = LocalDate.now();
        Date date = java.sql.Date.valueOf(now);
        Grading grading = Grading.builder()
                .teachingLoadDetails(teachingLoadDetails)
                .term(term)
                .description("Attendance for"+ " " + date)
                .dateConducted(date)
                .numberOfItems(1)
                .category(category)
                .build();

       boolean checkDate = gradingRepository.existsByDateConductedAndTermIdAndCategoryIdAndTeachingLoadDetailsId
               (date, term.getId(), category.getId(), teachingLoadDetails.getId());

        if (checkDate) {
            throw new IllegalArgumentException("Attendance sheet for today is already created :-)");
        }

        grading = gradingRepository.save(grading);

        return GradingResponse.builder()
                .id(grading.getId())
                .desc(grading.getDescription())
                .numberOfItems(grading.getNumberOfItems())
                .catId(category.getId())
                .teachingLoadDetailId(request.getTeachingLoadDetailId())
                .termId(request.getTermId())
                .date(grading.getDateConducted())
                .termId(request.getTermId())
                .build();
    }

    public void recordAttendance(AttendanceRecord request) {

        Enrollments enrollment = enrollmentRepository
                .faFindByStudentIdAndTeachingLoadDetailId(
                        request.getStudentId(), request.getTeachingLoadDetailId()
                );

        if(enrollment == null) {
            throw new RuntimeException("Enrollment not found");
        }

        Grading grading = gradingRepository
                .findTopByTeachingLoadDetailsIdAndCategoryIdAndTermIdOrderByDateConducted(
                        request.getTeachingLoadDetailId(), 4, request.getTermId()
                )
                .orElseThrow(() -> new RuntimeException("Latest attendance grading not found"));

        GradingDetail gradingDetail = GradingDetail.builder()
                .enrollments(enrollment)
                .grading(grading)
                .score(1.0)
                .recordedOn(new Date())
                .build();

        gradingDetailRepository.save(gradingDetail);

    }

    public List<AttendanceResponse> getAllAttendanceInTeachingLoadDetailAndTerm(Integer teachingLoadDetailId, Integer termId) {

        GradeCategory cat = gradingFunc.findGradeCategory(4);

        if (cat == null) {
            throw new RuntimeException("Grade category not found");
        }

        List<Grading> grading = gradingRepository.findByTeachingLoadDetailsIdAndTermIdAndCategoryId(
                teachingLoadDetailId,
                termId,
                cat.getId()
        );

        if (grading == null || grading.isEmpty()) {
            return Collections.emptyList();
        }

        return grading.stream()
                .map(detail -> new AttendanceResponse(
                        detail.getId(),
                        detail.getDescription(),
                        detail.getNumberOfItems(),
                        detail.getDateConducted()
                ))
                .collect(Collectors.toList());
    }


}