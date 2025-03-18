package com.assookkaa.ClassRecord.Dto.Response.Enrollment;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class EnrollmentResponseDto {
    private Integer id;
    private Integer studentId;
    private Integer teachingLoadDetailId;
    private Date enrollmentDate;
}
