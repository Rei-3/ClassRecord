package com.assookkaa.ClassRecord.Dto.Response.GradingComposition;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GradingCompositionDtoResponse {
    private Integer id;
    private double percentage;
    private Integer categoryId;
    private Integer teachingLoadDetailId;
}

