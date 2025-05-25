package com.assookkaa.ClassRecord.Service.Attendance.Interface;


import com.assookkaa.ClassRecord.Dto.Request.Attendance.AttendanceBuildRequest;
import com.assookkaa.ClassRecord.Dto.Request.Attendance.AttendanceRecord;
import com.assookkaa.ClassRecord.Dto.Request.Attendance.AttendanceRecordRequest;

import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingRequest;
import com.assookkaa.ClassRecord.Dto.Response.Attendance.AttendanceResponse;
import com.assookkaa.ClassRecord.Dto.Response.Grading.GradingResponse;

import java.util.List;


public interface AttendanceInterface {
    GradingResponse buildAttendance(AttendanceBuildRequest request);
    void recordAttendance(AttendanceRecord request);
    List<AttendanceResponse> getAllAttendanceInTeachingLoadDetailAndTerm
            (Integer teachingLoadDetailId, Integer termId);

}
