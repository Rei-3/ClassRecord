package com.assookkaa.ClassRecord.Utils.Interface.Attendance;


import com.assookkaa.ClassRecord.Dto.Request.Attendance.AttendanceBatchBuildRequest;
import com.assookkaa.ClassRecord.Dto.Request.Attendance.AttendanceRecordRequest;
import com.assookkaa.ClassRecord.Dto.Response.Attendance.AttendanceResponse;
import com.assookkaa.ClassRecord.Entity.Attendance;
import com.assookkaa.ClassRecord.Entity.Enrollments;

import java.util.List;

public interface AttendanceFuncInterface {

  //build
  Attendance buildActivity (AttendanceRecordRequest attendanceRecordRequest,
                            Enrollments enrollments);
  List <Enrollments> findTeachingLoadIdEnrollment(Integer teachingLoadId);
  void saveAll(List<Attendance> attendances);
  AttendanceResponse recordPresent (AttendanceRecordRequest attendanceRecordRequest);
}
