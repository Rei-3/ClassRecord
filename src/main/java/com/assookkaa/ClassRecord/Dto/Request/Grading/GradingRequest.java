package com.assookkaa.ClassRecord.Dto.Request.Grading;

import lombok.Data;

import java.util.Date;


@Data
public class GradingRequest {

    private String description;
    private Integer numberOfItems;
    private Integer categoryId;
    private Integer teachingLoadDetailId;
    private Integer termId;
    private Date date;
}
