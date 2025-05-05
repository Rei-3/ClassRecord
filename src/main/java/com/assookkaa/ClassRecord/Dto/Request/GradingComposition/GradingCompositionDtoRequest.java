package com.assookkaa.ClassRecord.Dto.Request.GradingComposition;

import lombok.Data;

@Data
public class GradingCompositionDtoRequest {
    private Double percentage;
    private Integer categoryId;
    private Integer teachingLoadDetailId;
}
