package com.assookkaa.ClassRecord.Dto.Response.Grading;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class GradingResponse {
    private Integer id;
    private String desc;
    private Integer numberOfItems;
    private Integer catId;
    private Integer termId;
    private Integer teachingLoadDetailId;
    private Date date;
    private List<GradingDetailsResponse> details;
}
