package com.assookkaa.ClassRecord.Dto.Response.Grading;

import com.assookkaa.ClassRecord.Entity.Term;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeComputation {
    private Integer studentId;
    private String name;
    private Map <String, BigDecimal> scores;
    private BigDecimal finalGrade;
    private String remarks;
}
