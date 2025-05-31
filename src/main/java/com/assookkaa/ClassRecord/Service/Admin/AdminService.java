package com.assookkaa.ClassRecord.Service.Admin;

import com.assookkaa.ClassRecord.Dto.Request.Admin.TeachingLoadStatusEdit;
import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.TeachingLoadDetailsListOfStudentsEnrolled;
import com.assookkaa.ClassRecord.Dto.Response.User.StudentAllDetails;
import com.assookkaa.ClassRecord.Dto.Response.User.TeacherAllDetails;
import com.assookkaa.ClassRecord.Dto.Response.User.Users;
import com.assookkaa.ClassRecord.Entity.*;
import com.assookkaa.ClassRecord.Repository.*;
import com.assookkaa.ClassRecord.Service.Admin.Interface.AdminInterface;
import com.assookkaa.ClassRecord.Service.Teacher.TeachingLoadService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService implements AdminInterface {


    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final CoursesRepository coursesRepository;
    private final TeacherRepository teacherRepository;
    private final TeachingLoadService teachingLoadService;
    private final TeachingLoadRepository teachingLoadRepository;

    public AdminService(UserRepository userRepository, StudentRepository studentRepository, CoursesRepository coursesRepository, TeacherRepository teacherRepository, TeachingLoadService teachingLoadService, TeachingLoadRepository teachingLoadRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.coursesRepository = coursesRepository;
        this.teacherRepository = teacherRepository;
        this.teachingLoadService = teachingLoadService;
        this.teachingLoadRepository = teachingLoadRepository;
    }

    @Override
    public List<Users> getAllUsers() {
       List <User> users = userRepository.findAll();
        return users.stream().map(
                s-> Users.builder()
                        .id(s.getId())
                        .fname(s.getFname())
                        .mname(s.getMname())
                        .lname(s.getLname())
                        .gender(s.getGender())
                        .dob(s.getDob())
                        .username(s.getUsername())
                        .email(s.getEmail())
                        .otp(s.getOtp())
                        .role(s.getRole().getRoleName())
                        .build()
        ).collect(Collectors.toList());
    }

    @Override
    public List<StudentAllDetails> getAllStudentDetails() {
        List<User> users = userRepository.findAllByRoleId(3); // Fetch all students

        return users.stream()
                .map(s -> {
                    // Find the corresponding student record
                    Optional<Students> students = studentRepository.findByUsername(s.getUsername());

                    if (students.isEmpty()) {
                        return null;
                    }
                    Courses course = coursesRepository.findByStudentsId(students.get().getId());

                    return StudentAllDetails.builder()
                            .id(s.getId())
                            .fname(s.getFname())
                            .mname(s.getMname())
                            .lname(s.getLname())
                            .gender(s.getGender())
                            .dob(s.getDob())
                            .username(s.getUsername())
                            .email(s.getEmail())
                            .otp(s.getOtp())
                            .role(s.getRole().getRoleName())
                            .studentId(String.valueOf(students.get().getStudentId()))
                            .courseName(course.getCourse_code() + "-" + course.getCourse_name())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherAllDetails> getAllTeacherDetails() {
        List<User> users = userRepository.findAllByRoleId(2);

        return users.stream()
                .map(t->{
                    Teachers teach = teacherRepository.findTeacherByUsername(t.getUsername());

                    if (teach == null) {
                        return TeacherAllDetails.builder()
                                .id(t.getId())
                                .fname(t.getFname())
                                .mname(t.getMname())
                                .lname(t.getLname())
                                .gender(t.getGender())
                                .dob(t.getDob())
                                .username(null)
                                .email(t.getEmail())
                                .otp(t.getOtp())
                                .role(t.getRole().getRoleName())
                                .build();
                    }

                    return TeacherAllDetails.builder()
                            .id(t.getId())
                            .fname(t.getFname())
                            .mname(t.getMname())
                            .lname(t.getLname())
                            .gender(t.getGender())
                            .dob(t.getDob())
                            .username(t.getUsername())
                            .email(t.getEmail())
                            .otp(t.getOtp())
                            .role(t.getRole().getRoleName())
                            .teacherId(String.valueOf(teach.getTeacherId()))
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<TeachingLoadDetailsListOfStudentsEnrolled> getAllEnrolledStudentDetails(Integer tld) {
        teachingLoadService.viewAllEnrolledStudents(tld);
        return List.of();
    }

    @Override
    public TeachingLoadStatusEdit editStatus (TeachingLoadStatusEdit dto) {

        TeachingLoad teach = teachingLoadRepository.findById(dto.getTeachingLoadId()).orElseThrow();

        teach.setStatus(dto.getStatus());

        teachingLoadRepository.save(teach);

        return new TeachingLoadStatusEdit();
    }

    public void addUser (){
//
//        User.builder()
//                .fname()
//                .build();

    }


}
