package com.assookkaa.ClassRecord.Controller.Auth;

import com.assookkaa.ClassRecord.Config.Filter.JwtUtil;
import com.assookkaa.ClassRecord.Dto.Request.*;
import com.assookkaa.ClassRecord.Dto.Response.*;
import com.assookkaa.ClassRecord.Utils.ApiException;
import com.assookkaa.ClassRecord.Service.Auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthService authService;

    public AuthController(JwtUtil jwtUtil, AuthService authService) {
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login (@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @PostMapping("/register-teacher")
    public ResponseEntity<?> registerUser(@RequestBody RegisterTeacherDto registerDto) {
        try {
            RegisterTeacherResponseDto registeredUser = authService.registerTeacher(registerDto);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (ApiException e) {
            ErrorDtoResponse errorDtoResponse = new ErrorDtoResponse(e.getStatusCode(), e.getMessage(), e.getErrorType());
            return new ResponseEntity<>(errorDtoResponse, HttpStatus.valueOf(e.getStatusCode()));
        }
    }

    @PostMapping("/register-student")
    public ResponseEntity<?> registerStudent(@RequestBody RegisterStudentDto registerDto) {
        try {
            RegisterStudentDtoResponse registerStudent = authService.registerStudent(registerDto);
            return new ResponseEntity<>(registerStudent, HttpStatus.CREATED);
        } catch (ApiException e) {
            ErrorDtoResponse errorDtoResponse = new ErrorDtoResponse(e.getStatusCode(), e.getMessage(), e.getErrorType());
            return new ResponseEntity<>(errorDtoResponse, HttpStatus.valueOf(e.getStatusCode()));
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String otp) {
        boolean optValid = authService.otpValidation(otp);
        if (optValid) {
            return ResponseEntity.ok("OTP verified");
        } else {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }
    }

    @PostMapping("/teacher-username-password")
    public ResponseEntity<?> teacherUsernamePassword(
            @RequestParam String otp,
            @RequestBody UsernameAndPasswordDto usernameAndPasswordDto
    ) {
        try {
            boolean otpValidation = authService.otpValidation(otp);
            if (!otpValidation) {
                ErrorDtoResponse errorDtoResponse = new ErrorDtoResponse(400, "OTP Invalid", "OTP_INVALID");
                return new ResponseEntity<>(errorDtoResponse, HttpStatus.BAD_REQUEST);
            }

            UsernameAndPasswordDtoResponse teacherUsernamePassword = authService.teacherUsernameAndPassword(otp, usernameAndPasswordDto);
            return new ResponseEntity<>(teacherUsernamePassword, HttpStatus.CREATED);
        } catch (ApiException e) {
            ErrorDtoResponse errorDtoResponse = new ErrorDtoResponse(e.getStatusCode(), e.getMessage(), e.getErrorType());
            return new ResponseEntity<>(errorDtoResponse, HttpStatus.valueOf(e.getStatusCode()));
        }
    }

    @PostMapping("/student-username-password")
    public ResponseEntity<?> studentUsernamePassword(
            @RequestParam String otp,
            @RequestBody UsernameAndPasswordDto usernameAndPasswordDto) {
        try {
            boolean otpValidation = authService.otpValidation(otp);
            if (!otpValidation) {
                ErrorDtoResponse errorDtoResponse = new ErrorDtoResponse(400, "OTP Invalid", "OTP_INVALID");
                return new ResponseEntity<>(errorDtoResponse, HttpStatus.BAD_REQUEST);
            }
            UsernameAndPasswordDtoResponse studentUsernamePassword = authService.studentUsernameAndPassword(otp, usernameAndPasswordDto);
            return new ResponseEntity<>(studentUsernamePassword, HttpStatus.CREATED);
        } catch (ApiException e) {
            ErrorDtoResponse errorDtoResponse = new ErrorDtoResponse(e.getStatusCode(), e.getMessage(), e.getErrorType());
            return new ResponseEntity<>(errorDtoResponse, HttpStatus.valueOf(e.getStatusCode()));
        }
    }


}


