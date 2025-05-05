package com.assookkaa.ClassRecord.Dto.Request.Grading;

import lombok.Data;


@Data
public class GradingUpdateRequest {
    private Integer gradingDetailId;
    private Double score;
}
