package com.assookkaa.ClassRecord.Dto.Response.Grading;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GradingDetailsResponse {
    private Integer id;
    private String status;
    private Integer enrollmentId;
    private Integer gradingId;
    private Double score;
}
