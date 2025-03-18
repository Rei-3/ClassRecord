package com.assookkaa.ClassRecord.Service.Auth;

import com.assookkaa.ClassRecord.Config.Filter.JwtUtil;
import com.assookkaa.ClassRecord.Dto.Request.LoginDto;
import com.assookkaa.ClassRecord.Dto.Request.RegisterStudentDto;
import com.assookkaa.ClassRecord.Dto.Request.RegisterTeacherDto;
import com.assookkaa.ClassRecord.Dto.Request.UsernameAndPasswordDto;
import com.assookkaa.ClassRecord.Dto.Response.LoginResponseDto;
import com.assookkaa.ClassRecord.Dto.Response.RegisterStudentDtoResponse;
import com.assookkaa.ClassRecord.Dto.Response.RegisterTeacherResponseDto;
import com.assookkaa.ClassRecord.Dto.Response.UsernameAndPasswordDtoResponse;
import com.assookkaa.ClassRecord.Entity.*;
import com.assookkaa.ClassRecord.Repository.*;
import com.assookkaa.ClassRecord.Service.OtpService;
import com.assookkaa.ClassRecord.Utils.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final TeacherRepository teacherRepository;
    private final OtpService otpService;
    private final PasswordEncoder passwordEncoder;
    private final ValidationService validationService;
    private final StudentRepository studentRepository;
    private final CoursesRepository coursesRepository;

    @Transactional
    public RegisterTeacherResponseDto registerTeacher(RegisterTeacherDto registerTeacherDto) {
        Roles role = rolesRepository.findByRoleName("teacher");
        validationService.RoleChecker(role);
        validationService.TeacherValidationChecker(registerTeacherDto.getEmail(), registerTeacherDto.getTeacher_id());

        User user = User.builder()
                .fname(registerTeacherDto.getFname())
                .mname(registerTeacherDto.getMname())
                .lname(registerTeacherDto.getLname())
                .email(registerTeacherDto.getEmail())
                .dob(registerTeacherDto.getDob())
                .gender(registerTeacherDto.getGender())
                .role(role)
                .build();

        User savedUser = userRepository.save(user);

            otpService.sendOtpEmail(savedUser.getEmail());

        RegisterTeacherResponseDto teacherResponseDto = new RegisterTeacherResponseDto();
        teacherResponseDto.setUser_id(savedUser.getId());
        teacherResponseDto.setFname(savedUser.getFname());
        teacherResponseDto.setMname(savedUser.getMname());
        teacherResponseDto.setLname(savedUser.getLname());
        teacherResponseDto.setDob(savedUser.getDob());
        teacherResponseDto.setEmail(savedUser.getEmail());
        teacherResponseDto.setGender(savedUser.getGender());

        Teachers teachers = Teachers.builder()
                .teacher(user)
                .teacherId(registerTeacherDto.getTeacher_id())
                .build();

        Teachers savedTeachers = teacherRepository.save(teachers);
        teacherResponseDto.setTeacher_id(savedTeachers.getTeacherId());
        teacherResponseDto.setId(savedTeachers.getId());

        return teacherResponseDto;
    }

    public boolean otpValidation(String otp) {
        User userOtp = userRepository.findByOtp(otp);
        return userOtp != null;
    }

    @Transactional
    public RegisterStudentDtoResponse registerStudent(RegisterStudentDto registerStudentDto) {
        Roles role = rolesRepository.findByRoleName("student");
        validationService.RoleChecker(role);
        validationService.StudentValidationChecker(registerStudentDto.getEmail(), registerStudentDto.getStudentId());

        User user = User.builder()
                .fname(registerStudentDto.getFname())
                .mname(registerStudentDto.getMname())
                .lname(registerStudentDto.getLname())
                .email(registerStudentDto.getEmail())
                .dob(registerStudentDto.getDob())
                .gender(registerStudentDto.getGender())
                .role(role)
                .build();
        User savedUser = userRepository.save(user);

            otpService.sendOtpEmail(savedUser.getEmail());

        RegisterStudentDtoResponse student = new RegisterStudentDtoResponse();
        student.setFname(savedUser.getFname());
        student.setMname(savedUser.getMname());
        student.setLname(savedUser.getLname());
        student.setEmail(savedUser.getEmail());
        student.setDob(savedUser.getDob());
        student.setGender(savedUser.getGender());

        return student;
    }

    public UsernameAndPasswordDtoResponse usernameAndPassword(String otp, UsernameAndPasswordDto usernameAndPasswordDto, Boolean isStudent) {
        User user = userRepository.findByOtp(otp);
        if (user == null) {
            throw new ApiException("OTP not found", 409, "OTP_NOT_FOUND");
        }
        validationService.UsernameChecker(usernameAndPasswordDto.getUsername());

        if (isStudent) {
            Courses course = findCourseId(usernameAndPasswordDto.getCourseId());
            Students studentId = Students.builder()
                    .course(course)
                    .studentId(Integer.valueOf(usernameAndPasswordDto.getUsername()))
                    .student(user)
                    .build();
            studentRepository.save(studentId);
        }

        user.setUsername(usernameAndPasswordDto.getUsername());
        user.setPassword(passwordEncoder.encode(usernameAndPasswordDto.getPassword()));
        userRepository.save(user);

        UsernameAndPasswordDtoResponse response = new UsernameAndPasswordDtoResponse();
        response.setUsername(user.getUsername());
        return response;
    }

    public UsernameAndPasswordDtoResponse studentUsernameAndPassword(String otp, UsernameAndPasswordDto usernameAndPasswordDto) {
        return usernameAndPassword(otp, usernameAndPasswordDto, true);
    }

    public UsernameAndPasswordDtoResponse teacherUsernameAndPassword(String otp, UsernameAndPasswordDto usernameAndPasswordDto) {
        return usernameAndPassword(otp, usernameAndPasswordDto, false);
    }

    public LoginResponseDto login(LoginDto loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
        {
            String token = jwtUtil.generateAccessToken(user.getUsername(), user.getOtp(), user.getId());
            return new LoginResponseDto(user.getUsername(), token);
        }
        throw new ApiException("Invalid username or password", 403, "INVALID_USERNAME_OR_PASSWORD");
    }

    private Courses findCourseId (Integer courseId) {
        return coursesRepository.findById(courseId).orElseThrow(() -> new ApiException("Course not found", 404, "COURSE_NOT_FOUND"));
    }
}
