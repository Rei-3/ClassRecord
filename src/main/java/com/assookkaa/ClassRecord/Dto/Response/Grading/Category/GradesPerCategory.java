package com.assookkaa.ClassRecord.Dto.Response.Grading.Category;

import com.assookkaa.ClassRecord.Entity.Grading;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor

public class GradesPerCategory {
    private Integer gradingId;
    private Integer gradingDetailId;
    private Integer enrollmentId;
    private String description;
    private Date conductedOn;
    private Integer numberOfItems;
    private Double score;
    private Date recordedOn;
}
