package com.assookkaa.ClassRecord.Dto.Request.Attendance;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class AttendanceBuildRequest {
    private Integer teachingLoadDetailId;
    private Integer termId;
    private Integer categoryId;

}
