package com.assookkaa.ClassRecord.Dto.Response.Grading;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseGradeResponse {
    private Integer id;
    private Double baseGrade;
    private Double percentage;
}
