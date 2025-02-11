package com.assookkaa.ClassRecord.Service.Teacher;

import com.assookkaa.ClassRecord.Dto.Request.GradingComposition.GradingCompositionDtoRequest;
import com.assookkaa.ClassRecord.Dto.Response.GradingComposition.GradingCompositionDtoResponse;
import com.assookkaa.ClassRecord.Entity.GradeCategory;
import com.assookkaa.ClassRecord.Entity.GradingComposition;
import com.assookkaa.ClassRecord.Entity.TeachingLoadDetails;
import com.assookkaa.ClassRecord.Repository.GradeCategoryRepository;
import com.assookkaa.ClassRecord.Repository.GradingCompositionRepository;
import com.assookkaa.ClassRecord.Repository.TeachingLoadDetailsRespository;
import com.assookkaa.ClassRecord.Utils.ApiException;
import com.assookkaa.ClassRecord.Service.Teacher.Interface.GradingCompositionInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GradingCompositionService implements GradingCompositionInterface {

    private final TeachingLoadDetailsRespository teachingLoadDetailsRespository;
    private final GradeCategoryRepository gradeCategoryRepository;
    private final GradingCompositionRepository gradingCompositionRepository;

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

    private TeachingLoadDetails findTeachingLoadDetailId(Integer id) {
        return teachingLoadDetailsRespository.findById(id).orElseThrow(()->
                new ApiException("Teaching Load Details Not Found", 404, "DETAILS_NOT_FOND"));
    }
    private GradeCategory findGradeCategory(Integer id) {
        return gradeCategoryRepository.findById(id).orElseThrow(()->
                new ApiException("Grading Category Not Found", 404, "DETAILS_NOT_FOND"));
    }

    private GradingComposition buildGradingComposition(GradingCompositionDtoRequest dto,
                                                       TeachingLoadDetails details,
                                                       GradeCategory gradeCategory) {
        return GradingComposition.builder()
                .percentage(dto.getPercentage())
                .category(gradeCategory)
                .teachingLoadDetail(details)
                .build();
    }


    private GradingCompositionDtoResponse mapToGradingCompositionDtoResponse(GradingComposition gradingComposition) {
        return GradingCompositionDtoResponse.builder()
                .id(gradingComposition.getId())
                .percentage(gradingComposition.getPercentage())
                .categoryId(gradingComposition.getCategory().getId())
                .teachingLoadDetailId(gradingComposition.getTeachingLoadDetail().getId())
                .build();
    }
}

