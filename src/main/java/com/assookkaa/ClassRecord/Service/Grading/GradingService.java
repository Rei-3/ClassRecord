package com.assookkaa.ClassRecord.Service.Grading;

import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingDetailRequest;
import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingRequest;
import com.assookkaa.ClassRecord.Dto.Response.Grading.BatchGradingDetailsResponse;
import com.assookkaa.ClassRecord.Dto.Response.Grading.GradingDetailsResponse;
import com.assookkaa.ClassRecord.Dto.Response.Grading.GradingResponse;
import com.assookkaa.ClassRecord.Entity.*;
import com.assookkaa.ClassRecord.Repository.EnrollmentRepository;
import com.assookkaa.ClassRecord.Repository.GradingDetailRepository;
import com.assookkaa.ClassRecord.Repository.GradingRepository;
import com.assookkaa.ClassRecord.Service.Grading.Interface.GradingInterface;
import com.assookkaa.ClassRecord.Utils.Objects.Grading.GradingFunc;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradingService implements GradingInterface {


    private final GradingFunc gradingFunc;
    private final GradingRepository gradingRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final GradingDetailRepository gradingDetailRepository;

    public GradingService(GradingFunc gradingFunc, GradingRepository gradingRepository, EnrollmentRepository enrollmentRepository, GradingDetailRepository gradingDetailRepository) {
        this.gradingFunc = gradingFunc;
        this.gradingRepository = gradingRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.gradingDetailRepository = gradingDetailRepository;
    }

    @Override
    @Transactional
    public GradingResponse addActivity(GradingRequest gradingRequest) {
        TeachingLoadDetails teachingLoadDetails =gradingFunc.findTeachingLoadDetailId(gradingRequest.getTeachingLoadDetailId());
        GradeCategory category = gradingFunc.findGradeCategory(gradingRequest.getCategoryId());
        Term term = gradingFunc.findTermById(gradingRequest.getTermId());
        Grading grading = gradingFunc.buildActivity(gradingRequest,teachingLoadDetails,category,term);

        grading = gradingRepository.save(grading);

        gradingFunc.batchGradingResponse(grading);

        List<Enrollments> enrollees = enrollmentRepository.findByTeachingLoadDetailId(teachingLoadDetails.getId());
        List<GradingDetail> gradingDetails = new ArrayList<>();

        for (Enrollments enrollment : enrollees) {
            GradingDetail gradingDetail = gradingFunc.inputStudentInTheScoreSheetRecord(
                    new GradingDetailRequest(),grading, enrollment);
            gradingDetails.add(gradingDetail);
        }

        gradingDetailRepository.saveAll(gradingDetails);

        List<GradingDetailsResponse> detailsResponses = gradingDetails.stream()
                .map(gradingFunc::gradingDetailResponse)
                .toList();

        return gradingFunc.batchGradingResponse(grading, detailsResponses);
    }

    @Override
    public BatchGradingDetailsResponse recordScore(List<GradingDetailRequest> gradingDetailRequests) {
        List<GradingDetail> gradingDetails = new ArrayList<>();

        for (GradingDetailRequest dto : gradingDetailRequests) {
            GradingDetail gradingDetail = gradingDetailRepository.findByGradingIdAndEnrollmentsId(
                    dto.getGradingId(),dto.getEnrollmentId())
                    .orElseThrow(()->new IllegalArgumentException("Not Found"));

            gradingDetail.setScore(dto.getScore());
            gradingDetail.setStatus(dto.getStatus() == null? "Missing": "Complied");

            gradingDetails.add(gradingDetail);
        }

       gradingDetailRepository.saveAll(gradingDetails);

        return BatchGradingDetailsResponse.builder()
                .message("Scores recorded successfully")
                .details(gradingDetails.stream()
                        .map(gradingFunc::gradingDetailResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    public List <GradingDetailsResponse> gradingDetailsResponseList (Integer gradingId){
        List<GradingDetail> gradingDetails = gradingDetailRepository.findByGradingId(gradingId);

        return gradingDetails.stream()
                .map(gradingFunc::gradingDetailResponse)
                .collect(Collectors.toList());
    }
}
