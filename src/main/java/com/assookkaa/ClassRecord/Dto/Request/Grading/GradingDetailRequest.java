package com.assookkaa.ClassRecord.Dto.Request.Grading;

import lombok.Data;

@Data
public class GradingDetailRequest {

    private Integer enrollmentId;
    private Integer gradingId;
    private Double score;

}
