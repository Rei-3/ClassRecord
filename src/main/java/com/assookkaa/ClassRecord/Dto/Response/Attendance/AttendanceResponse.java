package com.assookkaa.ClassRecord.Dto.Response.Attendance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
public class AttendanceResponse {
    private Integer id;
    private String description;
    private Integer numberOfItems;
    private Date date;
}
