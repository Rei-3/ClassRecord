package com.assookkaa.ClassRecord.Utils.Interface.Enrollments;

import com.assookkaa.ClassRecord.Dto.Request.Enrollment.EnrollmentRequestDto;
import com.assookkaa.ClassRecord.Dto.Response.Enrollment.EnrollmentResponseDto;
import com.assookkaa.ClassRecord.Entity.Enrollments;
import com.assookkaa.ClassRecord.Entity.Students;
import com.assookkaa.ClassRecord.Entity.TeachingLoadDetails;

public interface EnrollmentsFuncInterface {
 Enrollments buildEnrollment (EnrollmentRequestDto enrollmentRequestDto,
                                         Students students,
                                         TeachingLoadDetails teachingLoadDetails
                                        );
 EnrollmentResponseDto mapToEnrollmentResponse (Enrollments enrollments);
 Boolean isStudentAlreadyEnrolled(Students students, TeachingLoadDetails teachingLoadDetails);
}
