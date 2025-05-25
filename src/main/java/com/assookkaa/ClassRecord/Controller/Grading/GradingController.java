package com.assookkaa.ClassRecord.Controller.Grading;

import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingDetailRequest;
import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingRequest;
import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingUpdateRequest;
import com.assookkaa.ClassRecord.Dto.Response.Grading.*;
import com.assookkaa.ClassRecord.Dto.Response.Grading.Category.GradesPerCategory;
import com.assookkaa.ClassRecord.Entity.Enrollments;
import com.assookkaa.ClassRecord.Entity.GradingDetail;
import com.assookkaa.ClassRecord.Repository.EnrollmentRepository;
import com.assookkaa.ClassRecord.Service.Grading.GradingService;
import com.assookkaa.ClassRecord.Service.Grading.Interface.GradingInterface;
import com.assookkaa.ClassRecord.Utils.Objects.Super;
import com.assookkaa.ClassRecord.Utils.Token.TokenDecryption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/teacher/grading")
public class GradingController {

    private final GradingInterface gradingService;
    private final EnrollmentRepository enrollmentRepository;
    private final Super usurper;

    @Value("${api.key}")
    private String apiKey;
    @Value("${api.secret}")
    private String secretKey;

    public GradingController(GradingService gradingService, EnrollmentRepository enrollmentRepository, @Qualifier("super") Super usurper) {
        this.gradingService = gradingService;
        this.enrollmentRepository = enrollmentRepository;
        this.usurper = usurper;
    }

    TokenDecryption tokenDecryption = new TokenDecryption();

//POST

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/add-activity")
    public ResponseEntity<GradingResponse> addGrading (@RequestHeader("Authorization") String token,
                                                       @RequestBody GradingRequest gradingRequest) {
        tokenDecryption.tokenDecryption(token);
        GradingResponse response = gradingService.addActivity(gradingRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("details/record-score")
    public ResponseEntity<BatchGradingDetailsResponse> recordScore (
            @RequestHeader("Authorization") String token,
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
    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/details/edit-score")
    public ResponseEntity<UpdatedScoreResponse> editRecordedScore (@RequestHeader("Authorization") String token,
                                                                    @RequestBody GradingUpdateRequest gradingDetailRequests){
        tokenDecryption.tokenDecryption(token);
        return ResponseEntity.ok(gradingService.editRecordedScore(gradingDetailRequests));
    }

//GET
    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/details/{gradingId}")
    public ResponseEntity<List<GradingDetailsResponse>> details (@RequestHeader("Authorization") String token,
                                                           @PathVariable("gradingId") Integer gradingId) {

        tokenDecryption.tokenDecryption(token);
        List<GradingDetailsResponse> response = gradingService.gradingDetailsResponseList(gradingId);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/compute/{teachingLoadDetailId}/{termId}")
    public ResponseEntity<List<GradeComputation>> getFinalTermGrade(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer termId,
            @PathVariable Integer teachingLoadDetailId) {

        tokenDecryption.tokenDecryption(token);
        List<Enrollments> enrollments = enrollmentRepository.findByTeachingLoadDetailId(teachingLoadDetailId);

        List<GradeComputation> gradeComputations = gradingService.calculateEachTermGrade(teachingLoadDetailId, enrollments, termId);

        return ResponseEntity.ok(gradeComputations);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/compute/final/{teachingLoadDetailId}")
    public ResponseEntity<List<SemesterGradeComputation>> getComputedGrades(
            @PathVariable Integer teachingLoadDetailId,
            @RequestHeader("Authorization") String token) {

        tokenDecryption.tokenDecryption(token);
        List<Enrollments> enrollments = enrollmentRepository.findByTeachingLoadDetailId(teachingLoadDetailId);

        List<SemesterGradeComputation> semesterGrades = gradingService.calculateSemGrade(teachingLoadDetailId, enrollments);

        return ResponseEntity.ok(semesterGrades);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/details/grades-per-category/{teachingLoadDetailId}/{termId}/{categoryId}")
    public ResponseEntity <List<GradesPerCategory>> getGradesPerCategory(
            @PathVariable Integer teachingLoadDetailId,
//            @RequestHeader("API_KEY") String clientApiKey,
//            @RequestHeader("SECRET_KEY") String clientSecretKey,
            @PathVariable Integer termId,
            @PathVariable Integer categoryId,
            @RequestHeader ("Authorization") String token
    ){
//        usurper.checkKeys(clientApiKey, clientSecretKey);

            tokenDecryption.tokenDecryption(token);
            List<GradesPerCategory> response = gradingService.getGradesPerCategory(teachingLoadDetailId, termId, categoryId);
            return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/details/list-of-activities/{teachingLoadDetailId}/{termId}/{categoryId}")
    public ResponseEntity <List<GradingResponse>> getActivitiesList (
            @PathVariable Integer teachingLoadDetailId,
            @PathVariable Integer termId,
            @PathVariable Integer categoryId,
            @RequestHeader ("Authorization") String token
    ){
        tokenDecryption.tokenDecryption(token);
        List<GradingResponse> response = gradingService.getGrading(teachingLoadDetailId, termId, categoryId);
        return ResponseEntity.ok(response);
    }
}
