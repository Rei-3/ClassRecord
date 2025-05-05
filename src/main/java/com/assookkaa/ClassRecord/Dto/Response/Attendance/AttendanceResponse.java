package com.assookkaa.ClassRecord.Dto.Response.Attendance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
public class AttendanceResponse {
    private Integer id;
    private Integer enrollmentId;
    private Boolean isPresent;
    private LocalDateTime date;
}
