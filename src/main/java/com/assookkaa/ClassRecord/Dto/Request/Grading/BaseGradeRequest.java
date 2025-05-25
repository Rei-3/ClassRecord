package com.assookkaa.ClassRecord.Dto.Request.Grading;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseGradeRequest {
    private Integer teachingLoadDetailId;
    private Double baseGrade;
    private Double percentage;
}
