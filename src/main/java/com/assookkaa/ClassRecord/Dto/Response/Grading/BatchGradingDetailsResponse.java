package com.assookkaa.ClassRecord.Dto.Response.Grading;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class BatchGradingDetailsResponse {
    private String message;
    private List<GradingDetailsResponse> details;
}
