package com.assookkaa.ClassRecord.Controller.Admin;

import com.assookkaa.ClassRecord.Dto.Request.Admin.TeachingLoadStatusEdit;
import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.AllTeachingLoadResponse;
import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.TLD;
import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.TeachingLoadDetailsListOfStudentsEnrolled;
import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.TeachingLoadDetailsResponseDto;
import com.assookkaa.ClassRecord.Dto.Response.User.StudentAllDetails;
import com.assookkaa.ClassRecord.Dto.Response.User.TeacherAllDetails;
import com.assookkaa.ClassRecord.Dto.Response.User.Users;
import com.assookkaa.ClassRecord.Service.Admin.AdminService;
import com.assookkaa.ClassRecord.Service.Admin.Interface.AdminInterface;
import com.assookkaa.ClassRecord.Service.Sem.SemService;
import com.assookkaa.ClassRecord.Service.Teacher.TeachingLoadService;
import com.assookkaa.ClassRecord.Utils.Token.TokenDecryption;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final TokenDecryption tokenDecryption;
    private final AdminInterface adminService;
    private final TeachingLoadService teachingLoadService;
    private final SemService semService;

    public AdminController(TokenDecryption tokenDecryption, AdminService adminService, TeachingLoadService teachingLoadService, SemService semService) {
        this.tokenDecryption = tokenDecryption;
        this.adminService = adminService;
        this.teachingLoadService = teachingLoadService;
        this.semService = semService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all-users")
    public ResponseEntity<List<Users>> getAllUsers(
            @RequestHeader("Authorization") String token
    ) {
        tokenDecryption.tokenDecryption(token);
        List <Users> response = adminService.getAllUsers();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all-students")
    public ResponseEntity<List<StudentAllDetails>> getAllStudents(
            @RequestHeader("Authorization") String token
    ) {
        tokenDecryption.tokenDecryption(token);
        List <StudentAllDetails> response = adminService.getAllStudentDetails();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all-teachers")
    public ResponseEntity<List<TeacherAllDetails>> getAllTeachers(
            @RequestHeader("Authorization") String token
    ) {
        tokenDecryption.tokenDecryption(token);
        List <TeacherAllDetails> response = adminService.getAllTeacherDetails();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all-enrolled/{tld}")
    public ResponseEntity<List<TeachingLoadDetailsListOfStudentsEnrolled>> getAllEnrolledStudents(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer tld
    ){
        tokenDecryption.tokenDecryption(token);
        List<TeachingLoadDetailsListOfStudentsEnrolled> resp = teachingLoadService.viewAllEnrolledStudents(
                tld
        );
        return ResponseEntity.ok(resp);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all-teaching-loads")
    public ResponseEntity<List<AllTeachingLoadResponse>> getAllTeachingLoad (
            @RequestHeader ("Authorization") String token
    ){
        tokenDecryption.tokenDecryption(token);
        List <AllTeachingLoadResponse> resp = semService.getAllTeachingLoad();
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/all-tld")
    public ResponseEntity<List<TeachingLoadDetailsResponseDto>> getTLD (
            @RequestHeader ("Authorization") String token
    ){
        tokenDecryption.tokenDecryption(token);
        List<TeachingLoadDetailsResponseDto> resp = semService.getAllTeachingLoadDetails();
        return ResponseEntity.ok(resp);
    }


    @GetMapping("/all-tld/{teachingLoadId}")
    public ResponseEntity<List<TLD>> getTLDbyTL (
            @RequestHeader ("Authorization") String token,
            @PathVariable Integer teachingLoadId
    ){
        tokenDecryption.tokenDecryption(token);
        List<TLD> resp = semService.getTLDbyTLiD(teachingLoadId);
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/edit-teching-load")
    public ResponseEntity<TeachingLoadStatusEdit> editStatus(
            @RequestHeader ("Authorization") String token,
            @RequestBody TeachingLoadStatusEdit dto) {
        tokenDecryption.tokenDecryption(token);
        TeachingLoadStatusEdit updated = adminService.editStatus(dto);
        return ResponseEntity.ok(updated);
    }


}
