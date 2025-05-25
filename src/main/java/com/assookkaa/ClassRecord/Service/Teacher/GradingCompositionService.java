package com.assookkaa.ClassRecord.Service.Teacher;

import com.assookkaa.ClassRecord.Dto.Request.GradingComposition.GradingCompositionDtoRequest;
import com.assookkaa.ClassRecord.Dto.Request.GradingComposition.GradingCompositionEditRequest;
import com.assookkaa.ClassRecord.Dto.Response.GradingComposition.GradingCompositionDtoResponse;
import com.assookkaa.ClassRecord.Entity.GradeCategory;
import com.assookkaa.ClassRecord.Entity.GradingComposition;
import com.assookkaa.ClassRecord.Entity.TeachingLoadDetails;
import com.assookkaa.ClassRecord.Repository.*;
import com.assookkaa.ClassRecord.Service.Teacher.Interface.GradingCompositionInterface;
import com.assookkaa.ClassRecord.Utils.Objects.GradingCompostion.GradingCompostionFunc;
import com.assookkaa.ClassRecord.Utils.Token.TokenDecryption;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service

public class GradingCompositionService extends GradingCompostionFunc implements GradingCompositionInterface {


    private final GradingCompositionRepository gradingCompositionRepository;

    public GradingCompositionService(TeacherRepository teacherRepository, StudentRepository studentRepository, SubjectsRepository subjectsRepository, SemRepository semRepository, GradeCategoryRepository gradeCategoryRepository, TeachingLoadDetailsRespository teachingLoadDetailsRespository, TermRepository termRepository, EnrollmentRepository enrollmentRepository, TokenDecryption tokenDecryption, GradingCompositionRepository gradingCompositionRepository) {
        super(teacherRepository, studentRepository, subjectsRepository, semRepository, gradeCategoryRepository, teachingLoadDetailsRespository, termRepository, enrollmentRepository, tokenDecryption);
        this.gradingCompositionRepository = gradingCompositionRepository;
    }


    @Override
    public GradingCompositionDtoResponse addGradingComposition(String token, GradingCompositionDtoRequest gradingCompositionDtoRequest) {
        TeachingLoadDetails teachingLoadDetails = findTeachingLoadDetailId
                (gradingCompositionDtoRequest.getTeachingLoadDetailId());

        GradeCategory gradeCategory = findGradeCategory
                (gradingCompositionDtoRequest.getCategoryId());

        GradingComposition gradingComposition = buildGradingComposition
                (gradingCompositionDtoRequest, teachingLoadDetails, gradeCategory);
        gradingCompositionRepository.save(gradingComposition);

        return mapToGradingCompositionDtoResponse(gradingComposition);
        
    }

    public GradingCompositionDtoResponse editGradingComposition( GradingCompositionEditRequest dto) {
        GradingComposition existingComp = gradingCompositionRepository.findById(dto.getGradingCompositionId())
                .orElseThrow(()-> new RuntimeException("Grading Composition not found"));

        existingComp.setPercentage(dto.getPercentage());
        gradingCompositionRepository.save(existingComp);

        return mapToGradingCompositionDtoResponse(existingComp);
    }

//    private TeachingLoadDetails findTeachingLoadDetailId(Integer id) {
//        return teachingLoadDetailsRespository.findById(id).orElseThrow(()->
//                new ApiException("Teaching Load Details Not Found", 404, "DETAILS_NOT_FOND"));
//    }
//    private GradeCategory findGradeCategory(Integer id) {
//        return gradeCategoryRepository.findById(id).orElseThrow(()->
//                new ApiException("Grading Category Not Found", 404, "DETAILS_NOT_FOND"));
//    }
//
//    private GradingComposition buildGradingComposition(GradingCompositionDtoRequest dto,
//                                                       TeachingLoadDetails details,
//                                                       GradeCategory gradeCategory) {
//        return GradingComposition.builder()
//                .percentage(dto.getPercentage())
//                .category(gradeCategory)
//                .teachingLoadDetail(details)
//                .build();
//    }
//
//
//    private GradingCompositionDtoResponse mapToGradingCompositionDtoResponse(GradingComposition gradingComposition) {
//        return GradingCompositionDtoResponse.builder()
//                .id(gradingComposition.getId())
//                .percentage(gradingComposition.getPercentage())
//                .categoryId(gradingComposition.getCategory().getId())
//                .teachingLoadDetailId(gradingComposition.getTeachingLoadDetail().getId())
//                .build();
//    }
}

