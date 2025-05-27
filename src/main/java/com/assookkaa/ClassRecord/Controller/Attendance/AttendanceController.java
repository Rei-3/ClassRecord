package com.assookkaa.ClassRecord.Controller.Attendance;
import com.assookkaa.ClassRecord.Dto.Request.Attendance.AttendanceBuildRequest;
import com.assookkaa.ClassRecord.Dto.Request.Attendance.AttendanceRecord;

import com.assookkaa.ClassRecord.Dto.Response.Attendance.AttendanceResponse;

import com.assookkaa.ClassRecord.Dto.Response.Grading.GradingResponse;
import com.assookkaa.ClassRecord.Service.Attendance.AttendanceService;

import com.assookkaa.ClassRecord.Service.Attendance.Interface.AttendanceInterface;
import com.assookkaa.ClassRecord.Utils.Objects.Super;
import com.assookkaa.ClassRecord.Utils.Token.TokenDecryption;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teacher/attendance")
public class AttendanceController {

    private final TokenDecryption tokenDecryption;
    private final AttendanceInterface attendanceService;
    private final Super usurper;

    public AttendanceController(TokenDecryption tokenDecryption, AttendanceService attendanceService, @Qualifier("super") Super usurper) {
        this.tokenDecryption = tokenDecryption;
        this.attendanceService = attendanceService;
        this.usurper = usurper;
    }

    //Post
    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/make-attendance-sheet")
    public ResponseEntity<GradingResponse> makeAttendance(
            @RequestHeader ("Authorization") String token,
            @RequestBody AttendanceBuildRequest request,
            @RequestHeader("API_KEY") String apiKey,
            @RequestHeader("SECRET_KEY") String clientSecretKey
    ) {
        usurper.checkKeys(apiKey, clientSecretKey);
        tokenDecryption.tokenDecryption(token);
        try {
            GradingResponse response = attendanceService.buildAttendance(request);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
//------------------------------------
    //recored
    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/record-attendance")
    public ResponseEntity<Map<String, String>> recordPresent(
            @RequestHeader("Authorization") String token,
            @RequestBody AttendanceRecord request,
            @RequestHeader("API_KEY") String apiKey,
            @RequestHeader("SECRET_KEY") String clientSecretKey
    ) {

        usurper.checkKeys(apiKey, clientSecretKey);
        tokenDecryption.tokenDecryption(token);
        attendanceService.recordAttendance(request);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Attendance Recorded");
        return ResponseEntity.ok(response);
    }

//---------------------------------------------------
    //Get
    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/list-of-attendance/{teachingLoadDetailId}/{termId}")
    public List <AttendanceResponse> listAttendance(
            @RequestHeader ("Authorization") String token,
            @PathVariable Integer teachingLoadDetailId,
            @PathVariable Integer termId,
            @RequestHeader("API_KEY") String apiKey,
            @RequestHeader("SECRET_KEY") String clientSecretKey
    ){

        usurper.checkKeys(apiKey, clientSecretKey);
        tokenDecryption.tokenDecryption(token);
        List<AttendanceResponse> response = attendanceService.getAllAttendanceInTeachingLoadDetailAndTerm(teachingLoadDetailId, termId);
        return ResponseEntity.ok(response).getBody();
    }
}
