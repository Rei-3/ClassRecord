package com.assookkaa.ClassRecord.Controller.Auth;

import com.assookkaa.ClassRecord.Config.Filter.JwtUtil;
import com.assookkaa.ClassRecord.Dto.Request.*;
import com.assookkaa.ClassRecord.Dto.Response.*;
import com.assookkaa.ClassRecord.Entity.User;
import com.assookkaa.ClassRecord.Repository.UserRepository;
import com.assookkaa.ClassRecord.Utils.ApiException;
import com.assookkaa.ClassRecord.Service.Auth.AuthService;
import com.assookkaa.ClassRecord.Utils.Objects.Super;
import com.assookkaa.ClassRecord.Utils.Token.TokenDecryption;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name= "Auth", description = "Auth operations")
public class AuthController {

    private final AuthService authService;
    private final Super usurper;


    public AuthController(JwtUtil jwtUtil, AuthService authService, TokenDecryption tokenDecryption, @Qualifier("super") Super usurper) {
        this.authService = authService;
        this.usurper = usurper;
    }


    @PostMapping("/login")
    @Operation(summary = "Login to the App", description = "Enter credentials to login")
    public ResponseEntity<LoginResponseDto> login (
//            @RequestHeader("API_KEY") String apiKey,
//            @RequestHeader("SECRET_KEY") String clientSecretKey,
            @RequestBody LoginDto loginDto) {

//      usurper.checkKeys(apiKey, clientSecretKey);
        return ResponseEntity.ok(authService.login(loginDto));
    }


    @PostMapping("/register-teacher")
    @Operation(summary = "Register Teacher", description = "Enter credentials for registration")
    public ResponseEntity<?> registerUser(
            @Validated
            @RequestBody RegisterTeacherDto registerDto) {
        try {
            RegisterTeacherResponseDto registeredUser = authService.registerTeacher(registerDto);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (ApiException e) {
            ErrorDtoResponse errorDtoResponse = new ErrorDtoResponse(e.getStatusCode(), e.getMessage(), e.getErrorType());
            return new ResponseEntity<>(errorDtoResponse, HttpStatus.valueOf(e.getStatusCode()));
        }
    }


    @PostMapping("/register-student")
    @Operation(summary = "Register Student", description = "Enter credentials for student registration")
    public ResponseEntity<?> registerStudent(
            @Validated
            @RequestBody RegisterStudentDto registerDto) {
        try {
            RegisterStudentDtoResponse registerStudent = authService.registerStudent(registerDto);
            return new ResponseEntity<>(registerStudent, HttpStatus.CREATED);
        } catch (ApiException e) {
            ErrorDtoResponse errorDtoResponse = new ErrorDtoResponse(e.getStatusCode(), e.getMessage(), e.getErrorType());
            return new ResponseEntity<>(errorDtoResponse, HttpStatus.valueOf(e.getStatusCode()));
        }
    }

    @PostMapping("/verify-otp")
    @Operation(summary = "Verify OTP if its real", description = "Verify OTP")
    public ResponseEntity<?> verifyOtp(@RequestParam String otp) {
        boolean optValid = authService.otpValidation(otp);
        if (optValid) {
            return ResponseEntity.ok("OTP verified");
        } else {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/teacher-username-password")
    @Operation(summary = "Teacher Username and Password", description = "Enter Teacher Username and Password")
    public ResponseEntity<?> teacherUsernamePassword(
            @Validated
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

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/student-username-password")
    @Operation(summary = "Student ID and Password", description = "Enter Student ID and Password")
    public ResponseEntity<?> studentUsernamePassword(
            @Validated
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

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        try {
            String token = refreshTokenRequest.getRefreshToken();

            RefreshTokenResponse response = authService.RefreshToken(token);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}


