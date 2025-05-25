package com.assookkaa.ClassRecord.Dto.Request.GradingComposition;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GradingCompositionEditRequest {
    private Integer gradingCompositionId;
    private Double percentage;
}
