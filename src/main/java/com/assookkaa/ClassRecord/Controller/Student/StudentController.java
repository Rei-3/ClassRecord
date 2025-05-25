package com.assookkaa.ClassRecord.Controller.Student;

import com.assookkaa.ClassRecord.Dto.Response.Grading.Category.GradesPerCategory;
import com.assookkaa.ClassRecord.Dto.Response.Grading.GradeComputation;
import com.assookkaa.ClassRecord.Dto.Response.Grading.SemesterGradeComputation;
import com.assookkaa.ClassRecord.Dto.Response.Students.StudentsSubjectList;
import com.assookkaa.ClassRecord.Service.Student.StudentService;
import com.assookkaa.ClassRecord.Utils.Token.TokenDecryption;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final TokenDecryption tokenDecryption;
    private final StudentService studentService;

    public StudentController(TokenDecryption tokenDecryption, StudentService studentService) {
        this.tokenDecryption = tokenDecryption;
        this.studentService = studentService;
    }

    @GetMapping("/student-enrolled-subs")
    public ResponseEntity <List <StudentsSubjectList>> getStudentSubjectList(
            @RequestHeader ("Authorization") String token
    ) {

        String username = tokenDecryption.tokenDecryption(token);

        List<StudentsSubjectList> response = studentService.getEnrolledSubjects(username);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/student-term-grade/{teachingLoadDetailId}/{termId}")
    public ResponseEntity <GradeComputation> getStudentTermGrade(
            @RequestHeader ("Authorization") String token,
            @PathVariable Integer teachingLoadDetailId,
            @PathVariable Integer termId
    ){
        String username = tokenDecryption.tokenDecryption(token);

        GradeComputation response = studentService.getGradesPerTerm(username, teachingLoadDetailId, termId);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/student-sem-grade/{teachingLoadDetailId}")
    public ResponseEntity <SemesterGradeComputation> getStudentSemGrade(
            @RequestHeader ("Authorization") String token,
            @PathVariable Integer teachingLoadDetailId
    ){
        String username = tokenDecryption.tokenDecryption(token);
        SemesterGradeComputation response = studentService.getGradesPerSem(username, teachingLoadDetailId);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/student-scores/{teachingLoadDetailId}/{termId}/{catId}")
    public ResponseEntity <List<GradesPerCategory>> getStudentGradesPerCategory(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer teachingLoadDetailId,
            @PathVariable Integer termId,
            @PathVariable Integer catId
    ){
        String username = tokenDecryption.tokenDecryption(token);
        List <GradesPerCategory> response = studentService.getScorePerCat(username, teachingLoadDetailId, termId, catId);
        return ResponseEntity.ok(response);
    }
}
