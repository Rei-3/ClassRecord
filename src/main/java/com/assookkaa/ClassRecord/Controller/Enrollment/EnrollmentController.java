package com.assookkaa.ClassRecord.Controller.Enrollment;

import com.assookkaa.ClassRecord.Dto.Request.Enrollment.EnrollmentRequestDto;
import com.assookkaa.ClassRecord.Dto.Response.Enrollment.EnrollmentResponseDto;
import com.assookkaa.ClassRecord.Service.Enrollment.EnrollmentService;
import com.assookkaa.ClassRecord.Utils.Token.TokenDecryption;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    TokenDecryption tokenDecryption = new TokenDecryption();

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/enroll")
    public ResponseEntity <EnrollmentResponseDto> enrollToSubject (@RequestHeader ("Authorization") String token,
                                                                   @RequestBody EnrollmentRequestDto enrollmentRequestDto) {
        tokenDecryption.tokenDecryption(token);
        EnrollmentResponseDto responseDto = enrollmentService.addEnrollment(tokenDecryption.getToken(), enrollmentRequestDto);
        return ResponseEntity.ok(responseDto);
    }
}
