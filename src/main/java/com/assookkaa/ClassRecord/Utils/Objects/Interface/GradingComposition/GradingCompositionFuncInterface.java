package com.assookkaa.ClassRecord.Utils.Objects.Interface.GradingComposition;

import com.assookkaa.ClassRecord.Dto.Request.GradingComposition.GradingCompositionDtoRequest;
import com.assookkaa.ClassRecord.Dto.Response.GradingComposition.GradingCompositionDtoResponse;
import com.assookkaa.ClassRecord.Entity.GradeCategory;
import com.assookkaa.ClassRecord.Entity.GradingComposition;
import com.assookkaa.ClassRecord.Entity.TeachingLoadDetails;

public interface GradingCompositionFuncInterface {
    GradingComposition buildGradingComposition(GradingCompositionDtoRequest dto,
                                               TeachingLoadDetails details,
                                               GradeCategory gradeCategory);
    GradingCompositionDtoResponse mapToGradingCompositionDtoResponse(GradingComposition gradingComposition);
}
