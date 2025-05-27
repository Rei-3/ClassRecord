package com.assookkaa.ClassRecord.Controller.GradingComposition;

import com.assookkaa.ClassRecord.Dto.Request.Grading.BaseGradeRequest;
import com.assookkaa.ClassRecord.Dto.Request.GradingComposition.GradingCompositionDtoRequest;
import com.assookkaa.ClassRecord.Dto.Request.GradingComposition.GradingCompositionEditRequest;
import com.assookkaa.ClassRecord.Dto.Response.Grading.BaseGradeResponse;
import com.assookkaa.ClassRecord.Dto.Response.GradingComposition.GradingCompositionDtoResponse;
import com.assookkaa.ClassRecord.Service.Grading.GradingService;
import com.assookkaa.ClassRecord.Service.Teacher.GradingCompositionService;
import com.assookkaa.ClassRecord.Utils.Objects.Super;
import com.assookkaa.ClassRecord.Utils.Token.TokenDecryption;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher/grading-composition")
public class GradingCompositionController {

    private final GradingCompositionService gradingCompositionService;
    private final GradingService gradingService;
    TokenDecryption tokenDecryption = new TokenDecryption();
    private final Super usurper;

    public GradingCompositionController(GradingCompositionService gradingCompositionService, GradingService gradingService, @Qualifier("super") Super usurper) {
        this.gradingCompositionService = gradingCompositionService;
        this.gradingService = gradingService;
        this.usurper = usurper;
    }
//---------------------------------------------
    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("add-grading-composition")
    public ResponseEntity<GradingCompositionDtoResponse>addGradingComposition(@RequestHeader ("Authorization")String token,
                                                                              @RequestBody GradingCompositionDtoRequest gradingCompositionDtoRequest,
                                                                              @RequestHeader("API_KEY") String apiKey,
                                                                              @RequestHeader("SECRET_KEY") String clientSecretKey)
    {
        usurper.checkKeys(apiKey, clientSecretKey);
        String extractedToken = tokenDecryption.tokenDecryption(token);
        GradingCompositionDtoResponse response = gradingCompositionService.addGradingComposition(extractedToken, gradingCompositionDtoRequest);
        return ResponseEntity.ok(response);
    }
//------------------------------------------------
    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("edit-grading-composition")
    public ResponseEntity<GradingCompositionDtoResponse> editGradingComposition(
            @RequestHeader ("Authorization") String token,
            @RequestBody GradingCompositionEditRequest dto,
            @RequestHeader("API_KEY") String apiKey,
            @RequestHeader("SECRET_KEY") String clientSecretKey
    ){
        usurper.checkKeys(apiKey, clientSecretKey);
         tokenDecryption.tokenDecryption(token);
        GradingCompositionDtoResponse response = gradingCompositionService.editGradingComposition(dto);
        return ResponseEntity.ok(response);
    }
//------------------------------------------
    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("add-grade-mutation")
    public ResponseEntity <BaseGradeResponse> addGradeMutation(
            @RequestHeader ("Authorization") String token,
            @RequestBody BaseGradeRequest dto,
            @RequestHeader("API_KEY") String apiKey,
            @RequestHeader("SECRET_KEY") String clientSecretKey
    ){
        usurper.checkKeys(apiKey, clientSecretKey);
        tokenDecryption.tokenDecryption(token);
        BaseGradeResponse response = gradingService.addBaseGrade(dto);
        return ResponseEntity.ok(response);
    }
//----------------------------------------------------
    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("get-grade-mutation/{tld}")
    public ResponseEntity<BaseGradeResponse> getGradeMutation(
            @RequestHeader ("Authorization") String token,
            @PathVariable Integer tld,
            @RequestHeader("API_KEY") String apiKey,
            @RequestHeader("SECRET_KEY") String clientSecretKey
    ){
        usurper.checkKeys(apiKey, clientSecretKey);
        tokenDecryption.tokenDecryption(token);
        BaseGradeResponse response = gradingService.getBaseGrade(tld);
        return ResponseEntity.ok(response);
    }
//--------------------------------------------------
    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("edit-base-grade/{baseGradeId}")
    public ResponseEntity<BaseGradeResponse> editBaseGradeMutation(
            @RequestHeader ("Authorization") String token,
            @PathVariable Integer baseGradeId,
            @RequestBody BaseGradeRequest req,
            @RequestHeader("API_KEY") String apiKey,
            @RequestHeader("SECRET_KEY") String clientSecretKey
    ){
        usurper.checkKeys(apiKey, clientSecretKey);
        tokenDecryption.tokenDecryption(token);
        BaseGradeResponse response = gradingService.editBaseGrade(baseGradeId, req);
        return ResponseEntity.ok(response);
    }
}
