package com.assookkaa.ClassRecord.Dto.Request.Grading;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradingRequest {

    private String description;
    private Integer numberOfItems;
    private Integer categoryId;
    private Integer teachingLoadDetailId;
    private Integer termId;
    private Date date;

}


