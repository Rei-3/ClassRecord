package com.assookkaa.ClassRecord.Dto.Response.Grading;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdatedScoreResponse {
    private String message;
    private Double score;
}
