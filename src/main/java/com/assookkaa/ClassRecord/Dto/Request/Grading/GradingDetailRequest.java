package com.assookkaa.ClassRecord.Dto.Request.Grading;

import lombok.Data;

@Data
public class GradingDetailRequest {

    private Integer enrollmentId;
    private String status;
    private Integer gradingId;
    private Integer score;

}
