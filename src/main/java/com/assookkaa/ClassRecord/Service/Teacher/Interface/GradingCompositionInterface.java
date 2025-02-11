package com.assookkaa.ClassRecord.Service.Teacher.Interface;

import com.assookkaa.ClassRecord.Dto.Request.GradingComposition.GradingCompositionDtoRequest;
import com.assookkaa.ClassRecord.Dto.Response.GradingComposition.GradingCompositionDtoResponse;

public interface GradingCompositionInterface {
    GradingCompositionDtoResponse addGradingComposition(String token, GradingCompositionDtoRequest gradingCompositionDtoRequest);
}
