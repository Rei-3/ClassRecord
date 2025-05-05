package com.assookkaa.ClassRecord.Dto.Response.Grading;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SemesterGradeComputation {
    private Integer studentId;
    private String studentName;
    private Map<String , BigDecimal> termGrades;
    private BigDecimal semesterGrade;
    private String message;
}
