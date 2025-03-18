package com.assookkaa.ClassRecord.Utils.Objects.Grading;

import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingDetailRequest;
import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingRequest;
import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingUpdateRequest;
import com.assookkaa.ClassRecord.Dto.Response.Grading.GradingDetailsResponse;
import com.assookkaa.ClassRecord.Dto.Response.Grading.GradingResponse;
import com.assookkaa.ClassRecord.Entity.*;
import com.assookkaa.ClassRecord.Repository.*;
import com.assookkaa.ClassRecord.Utils.Interface.Grading.GradingFuncInterface;
import com.assookkaa.ClassRecord.Utils.Objects.Super;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class GradingFunc extends Super implements GradingFuncInterface {

    private final GradingDetailRepository gradingDetailRepository;


    public GradingFunc(TeacherRepository teacherRepository, StudentRepository studentRepository, SubjectsRepository subjectsRepository, SemRepository semRepository, GradeCategoryRepository gradeCategoryRepository, TeachingLoadDetailsRespository teachingLoadDetailsRespository, TermRepository termRepository, GradingDetailRepository gradingDetailRepository) {
        super(teacherRepository, studentRepository, subjectsRepository, semRepository, gradeCategoryRepository, teachingLoadDetailsRespository, termRepository);
        this.gradingDetailRepository = gradingDetailRepository;
    }

    @Override
    public Grading buildActivity(GradingRequest dto,
                                TeachingLoadDetails teachingLoadDetails,
                                GradeCategory category,
                                Term term)
    {
        return Grading.builder()
                .teachingLoadDetails(teachingLoadDetails)
                .description(dto.getDescription())
                .numberOfItems(dto.getNumberOfItems())
                .category(category)
                .term(term)
                .dateConducted(new Date())
                .build();
    }

    @Override
    public GradingDetail inputStudentInTheScoreSheetRecord(GradingDetailRequest dto,
                                                           Grading grading,
                                                           Enrollments enrollments)
    {
        String status = (dto.getScore() == null) ? "Missing" : "Complied";

        return GradingDetail.builder()
                .score(dto.getScore())
                .status(status)
                .grading(grading)
                .enrollments(enrollments)
                .build();
    }

    @Override
    public GradingDetail updateScoreOfStudent(GradingUpdateRequest dto) {

        GradingDetail gradingDetail = gradingDetailRepository.findById(dto.getGradingDetailId())
                .orElseThrow(() -> new RuntimeException("GradingDetail not found"));

        gradingDetail.setStatus((dto.getScore() == null) ? "Missing" : "Complied");
        gradingDetail.setScore(dto.getScore());
        return gradingDetailRepository.save(gradingDetail);
    }

    @Override
    public GradingResponse batchGradingResponse(Grading grading, List<GradingDetailsResponse>detailsResponses) {
        return GradingResponse.builder()
                .id(grading.getId())
                .numberOfItems(grading.getNumberOfItems())
                .desc(grading.getDescription())
                .catId(grading.getCategory().getId())
                .teachingLoadDetailId(grading.getTeachingLoadDetails().getId())
                .termId(grading.getTerm().getId())
                .date(grading.getDateConducted())
                .details(detailsResponses)
                .build();
    }
    public GradingResponse batchGradingResponse(Grading grading) {
        return GradingResponse.builder()
                .id(grading.getId())
                .numberOfItems(grading.getNumberOfItems())
                .desc(grading.getDescription())
                .catId(grading.getCategory().getId())
                .teachingLoadDetailId(grading.getTeachingLoadDetails().getId())
                .termId(grading.getTerm().getId())
                .date(grading.getDateConducted())

                .build();
    }

    @Override
    public GradingDetailsResponse gradingDetailResponse(GradingDetail gradingDetail) {
        return GradingDetailsResponse.builder()
                .id(gradingDetail.getId())
                .enrollmentId(gradingDetail.getEnrollments().getId())
                .gradingId(gradingDetail.getGrading().getId())
                .score(gradingDetail.getScore())
                .status(gradingDetail.getStatus())
                .build();
    }


}
