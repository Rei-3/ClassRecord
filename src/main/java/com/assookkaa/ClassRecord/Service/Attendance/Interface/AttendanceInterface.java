package com.assookkaa.ClassRecord.Service.Attendance.Interface;


import com.assookkaa.ClassRecord.Dto.Request.Attendance.AttendanceRecordRequest;

import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingRequest;
import com.assookkaa.ClassRecord.Dto.Response.Attendance.AttendanceResponse;
import com.assookkaa.ClassRecord.Dto.Response.Grading.GradingResponse;


public interface AttendanceInterface {
    GradingResponse buildAttendance(GradingRequest request);
    AttendanceResponse markAsPresent(AttendanceRecordRequest request);
}
