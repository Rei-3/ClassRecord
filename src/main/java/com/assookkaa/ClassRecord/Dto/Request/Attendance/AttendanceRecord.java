package com.assookkaa.ClassRecord.Dto.Request.Attendance;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttendanceRecord {
    private Integer studentId;
    private Integer teachingLoadDetailId;
    private Integer termId;
}
