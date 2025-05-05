package com.assookkaa.ClassRecord.Dto.Request.Attendance;

import lombok.Data;

import java.util.Date;

@Data
public class AttendanceBatchBuildRequest {
    private String description;
    private Integer numberOfItems;
    private Integer categoryId;
    private Integer teachingLoadDetailId;
    private Integer termId;
    private Date date;
}
