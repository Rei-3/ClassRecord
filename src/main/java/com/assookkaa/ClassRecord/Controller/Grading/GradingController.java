package com.assookkaa.ClassRecord.Controller.Grading;

import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingDetailRequest;
import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingRequest;
import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingUpdateRequest;
import com.assookkaa.ClassRecord.Dto.Response.Grading.*;
import com.assookkaa.ClassRecord.Dto.Response.Grading.Category.GradesPerCategory;
import com.assookkaa.ClassRecord.Entity.Enrollments;
import com.assookkaa.ClassRecord.Entity.Term;
import com.assookkaa.ClassRecord.Repository.EnrollmentRepository;
import com.assookkaa.ClassRecord.Service.Grading.GradingService;
import com.assookkaa.ClassRecord.Utils.Token.TokenDecryption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/grading")
public class GradingController {

    private final GradingService gradingService;
    private final EnrollmentRepository enrollmentRepository;

    public GradingController(GradingService gradingService, EnrollmentRepository enrollmentRepository) {
        this.gradingService = gradingService;
        this.enrollmentRepository = enrollmentRepository;
    }

    TokenDecryption tokenDecryption = new TokenDecryption();

//POST
    @PostMapping("/add-activity")
    public ResponseEntity<GradingResponse> addGrading (@RequestHeader("Authorization") String token,
                                                       @RequestBody GradingRequest gradingRequest) {
        tokenDecryption.tokenDecryption(token);
        GradingResponse response = gradingService.addActivity(gradingRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("details/record-score")
    public ResponseEntity<BatchGradingDetailsResponse> recordScore (@RequestHeader("Authorization") String token,
                                                                    @RequestBody List<GradingDetailRequest> gradingDetailRequest)
    {
        try{
            tokenDecryption.tokenDecryption(token);
            BatchGradingDetailsResponse response = gradingService.recordScore(gradingDetailRequest);
            return ResponseEntity.ok(response);
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

//PUT
    @PutMapping("/details/edit-score")
    public ResponseEntity<UpdatedScoreResponse> editRecordedScore (@RequestHeader("Authorization") String token,
                                                                    @RequestBody GradingUpdateRequest gradingDetailRequests){
        tokenDecryption.tokenDecryption(token);
        return ResponseEntity.ok(gradingService.editRecordedScore(gradingDetailRequests));
    }

//GET
    @GetMapping("/details/{gradingId}")
    public ResponseEntity<List<GradingDetailsResponse>> details (@RequestHeader("Authorization") String token,
                                                           @PathVariable("gradingId") Integer gradingId) {

        tokenDecryption.tokenDecryption(token);
        List<GradingDetailsResponse> response = gradingService.gradingDetailsResponseList(gradingId);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/compute/{teachingLoadDetailId}/{termId}")
    public ResponseEntity<List<GradeComputation>> getFinalTermGrade(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer termId,
            @PathVariable Integer teachingLoadDetailId) {

        tokenDecryption.tokenDecryption(token);
        List<Enrollments> enrollments = enrollmentRepository.findByTeachingLoadDetailId(teachingLoadDetailId);

        List<GradeComputation> gradeComputations = gradingService.calculateEachTermGrade(teachingLoadDetailId, enrollments, new Term(termId));

        return ResponseEntity.ok(gradeComputations);
    }


    @GetMapping("/compute/final/{teachingLoadDetailId}")
    public ResponseEntity<List<SemesterGradeComputation>> getComputedGrades(
            @PathVariable Integer teachingLoadDetailId,
            @RequestHeader("Authorization") String token) {

        tokenDecryption.tokenDecryption(token);
        List<Enrollments> enrollments = enrollmentRepository.findByTeachingLoadDetailId(teachingLoadDetailId);

        List<SemesterGradeComputation> semesterGrades = gradingService.calculateSemGrade(teachingLoadDetailId, enrollments);

        return ResponseEntity.ok(semesterGrades);
    }

    @GetMapping("/details/grades-per-category/{teachingLoadDetailId}/{termId}/{categoryId}")
    public ResponseEntity <GradesPerCategory> getGradesPerCategory(
            @PathVariable Integer teachingLoadDetailId,
            @PathVariable Integer termId,
            @PathVariable Integer categoryId,
            @RequestHeader ("Authorization") String token
    ){

            tokenDecryption.tokenDecryption(token);
            GradesPerCategory response = gradingService.getGradesPerCategory(teachingLoadDetailId, termId, categoryId);
            return ResponseEntity.ok(response);


    }
}
