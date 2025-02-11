package com.assookkaa.ClassRecord.Dto.Request.Enrollment;

import lombok.Data;

import java.util.Date;

@Data
public class EnrollmentRequestDto {
    private Integer teachingLoadDetailId;
    private Date enrollmentDate;
    private Integer courseId;
}
