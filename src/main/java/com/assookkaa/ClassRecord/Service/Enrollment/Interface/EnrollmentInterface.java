package com.assookkaa.ClassRecord.Service.Enrollment.Interface;

import com.assookkaa.ClassRecord.Dto.Request.Enrollment.EnrollmentRequestDto;
import com.assookkaa.ClassRecord.Dto.Response.Enrollment.EnrollmentResponseDto;

public interface EnrollmentInterface {
    EnrollmentResponseDto addEnrollment(String token, EnrollmentRequestDto enrollmentRequestDto);
}
