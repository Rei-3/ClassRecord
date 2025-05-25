package com.assookkaa.ClassRecord.Dto.Response.Grading;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GradingResponse {
    private Integer id;
    private String desc;
    private Integer numberOfItems;
    private Integer catId;
    private Integer termId;
    private Integer teachingLoadDetailId;
    private Date date;

}
