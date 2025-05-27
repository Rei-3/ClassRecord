package com.assookkaa.ClassRecord.Controller.Enrollment;

import com.assookkaa.ClassRecord.Dto.Request.Enrollment.EnrollmentRequestDto;
import com.assookkaa.ClassRecord.Dto.Response.Enrollment.EnrollmentResponseDto;
import com.assookkaa.ClassRecord.Dto.Response.User.StudentUser;
import com.assookkaa.ClassRecord.Service.Enrollment.EnrollmentService;
import com.assookkaa.ClassRecord.Service.Enrollment.Interface.EnrollmentInterface;
import com.assookkaa.ClassRecord.Service.Subject.SubjectService;
import com.assookkaa.ClassRecord.Utils.Objects.Super;
import com.assookkaa.ClassRecord.Utils.Token.TokenDecryption;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class EnrollmentController {
    private final EnrollmentInterface enrollmentService;
    private final SubjectService subjectService;
    TokenDecryption tokenDecryption = new TokenDecryption();
    private  final Super usurper;

    public EnrollmentController(EnrollmentService enrollmentService, SubjectService subjectService, @Qualifier("super") Super usurper) {
        this.enrollmentService = enrollmentService;
        this.subjectService = subjectService;
        this.usurper = usurper;
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/enroll")
    public ResponseEntity <EnrollmentResponseDto> enrollToSubject (@RequestHeader ("Authorization") String token,
                                                                   @Validated @RequestBody EnrollmentRequestDto enrollmentRequestDto,
                                                                   @RequestHeader("API_KEY") String apiKey,
                                                                   @RequestHeader("SECRET_KEY") String clientSecretKey) {
        usurper.checkKeys(apiKey, clientSecretKey);
        String extractedToken = tokenDecryption.tokenDecryption(token);
        EnrollmentResponseDto responseDto = enrollmentService.addEnrollment(extractedToken, enrollmentRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
//---------------------------------
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/student-info")
    public ResponseEntity <StudentUser> getStudentUser (
            @RequestHeader ("Authorization") String token,
            @RequestHeader("API_KEY") String apiKey,
            @RequestHeader("SECRET_KEY") String clientSecretKey
    ) {
        usurper.checkKeys(apiKey, clientSecretKey);
        String extractedToken = tokenDecryption.tokenDecryption(token);
        StudentUser response = subjectService.getStudentInfo(extractedToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
