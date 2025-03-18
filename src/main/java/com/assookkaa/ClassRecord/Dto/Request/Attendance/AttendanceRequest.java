package com.assookkaa.ClassRecord.Dto.Request.Attendance;

import lombok.Data;

import java.util.Date;

@Data
public class AttendanceRequest {
    private Integer enrollmentId;
    private Boolean isPresent;
    private Date date;
}
