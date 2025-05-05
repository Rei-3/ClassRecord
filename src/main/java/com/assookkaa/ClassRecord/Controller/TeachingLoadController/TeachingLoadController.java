package com.assookkaa.ClassRecord.Controller.TeachingLoadController;

import com.assookkaa.ClassRecord.Dto.Response.GradingComposition.GradingCompositionWithCategoryForTeachingLoadDetailResponse;
import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.TeachingLoadDetailsListOfStudentsEnrolled;
import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.TeachingLoadDetailsResponseDto;
import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.TeachingLoadResponseDto;
import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.TeachingLoadResponseSubjectsDto;
import com.assookkaa.ClassRecord.Entity.TeachingLoad;
import com.assookkaa.ClassRecord.Service.Subject.SubjectService;
import com.assookkaa.ClassRecord.Service.Teacher.Interface.TeachingLoadImplementation;
import com.assookkaa.ClassRecord.Utils.Token.TokenDecryption;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/class-record")
public class TeachingLoadController {


    private final TeachingLoadImplementation teachingLoadService;
    private final SubjectService subjectService;

    public TeachingLoadController(TeachingLoadImplementation teachingLoadService, SubjectService subjectService) {
        this.teachingLoadService = teachingLoadService;
        this.subjectService = subjectService;
    }

    TokenDecryption tokenDecryption = new TokenDecryption();

    @PostMapping("/add-teaching-load")
    public ResponseEntity<TeachingLoadResponseDto> teachingLoad(@RequestHeader("Authorization") String token,
                                                @RequestBody  TeachingLoadResponseDto teachingLoadResponseDto) {

        String extractedToken = tokenDecryption.tokenDecryption(token);

        TeachingLoadResponseDto responseDto = teachingLoadService.addTeachingLoad(extractedToken, teachingLoadResponseDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/add-teaching-load-details")
    public ResponseEntity<TeachingLoadDetailsResponseDto> teachingLoadDetails(
            @RequestHeader ("Authorization") String token,
            @RequestBody  TeachingLoadDetailsResponseDto teachingLoadDetailsResponseDto
    ){


        String extractedToken = tokenDecryption.tokenDecryption(token);

        TeachingLoadDetailsResponseDto responseDto = teachingLoadService.addTeachingLoadDetails(extractedToken, teachingLoadDetailsResponseDto);
        return ResponseEntity.ok(responseDto);
    }

    //Get
    @GetMapping("/view-enrolled/{teachingLoadDetailId}")
    public ResponseEntity<List<TeachingLoadDetailsListOfStudentsEnrolled>> viewEnrolled(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer teachingLoadDetailId
    ) {
        try {
            List<TeachingLoadDetailsListOfStudentsEnrolled> enrolledStudents = teachingLoadService.viewAllEnrolledStudents(teachingLoadDetailId);// Return the response with the list of enrolled students
            return ResponseEntity.ok(enrolledStudents);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/teaching-load")
    public ResponseEntity<List<TeachingLoadResponseSubjectsDto>> getCurrentTeacherLoads(
            @RequestHeader("Authorization") String token
    ) {
        // Decrypt the token
        String extractedToken = tokenDecryption.tokenDecryption(token);

        // Call the service method synchronously to get the teaching loads
        List<TeachingLoadResponseSubjectsDto> teachingLoads = teachingLoadService.getTeachingLoadsForCurrentTeacher(extractedToken);

        // Return the response with the list of teaching loads
        return ResponseEntity.ok(teachingLoads);
    }

    @GetMapping("/grading-composition/{teachingLoadDetailId}")
    public ResponseEntity<GradingCompositionWithCategoryForTeachingLoadDetailResponse> getGradingComposition (
            @RequestHeader ("Authorization") String token, @PathVariable Integer teachingLoadDetailId
    ){
        GradingCompositionWithCategoryForTeachingLoadDetailResponse resp =
                subjectService.getGradingCompositionWithCategoryForTeachingLoadDetail(teachingLoadDetailId);

        return ResponseEntity.ok(resp);
    }

}
