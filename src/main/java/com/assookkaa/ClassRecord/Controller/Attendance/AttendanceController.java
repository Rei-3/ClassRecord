package com.assookkaa.ClassRecord.Controller.Attendance;

import com.assookkaa.ClassRecord.Dto.Request.Attendance.AttendanceBatchBuildRequest;
import com.assookkaa.ClassRecord.Dto.Request.Attendance.AttendanceRecordRequest;
import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingDetailRequest;
import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingRequest;
import com.assookkaa.ClassRecord.Dto.Response.Attendance.AttendanceResponse;
import com.assookkaa.ClassRecord.Dto.Response.Grading.BatchGradingDetailsResponse;
import com.assookkaa.ClassRecord.Dto.Response.Grading.GradingResponse;
import com.assookkaa.ClassRecord.Service.Attendance.AttendanceService;
import com.assookkaa.ClassRecord.Utils.ApiResponse;
import com.assookkaa.ClassRecord.Utils.Token.TokenDecryption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final TokenDecryption tokenDecryption;
    private final AttendanceService attendanceService;

    public AttendanceController(TokenDecryption tokenDecryption, AttendanceService attendanceService) {
        this.tokenDecryption = tokenDecryption;
        this.attendanceService = attendanceService;
    }

    //Post
    @PostMapping("/make-attendance-sheet")
    public ResponseEntity<GradingResponse> makeAttendance(@RequestHeader ("Authorization") String token,
                                                          @RequestBody GradingRequest gradingRequest) {
        tokenDecryption.tokenDecryption(token);
        try {
            GradingResponse response = attendanceService.buildAttendance(gradingRequest);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    //Put
    @PutMapping("/record-attendance")
    public ResponseEntity<ApiResponse<AttendanceResponse>> recordPresent (@RequestHeader ("Authorization") String token,
                                                                          @RequestBody AttendanceRecordRequest request){

        AttendanceResponse response = attendanceService.markAsPresent(request);
        return ResponseEntity.ok(new ApiResponse<>("Attendance marked successfully", response));
    }
}
