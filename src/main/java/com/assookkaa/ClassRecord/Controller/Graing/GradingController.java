package com.assookkaa.ClassRecord.Controller.Graing;

import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingDetailRequest;
import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingRequest;
import com.assookkaa.ClassRecord.Dto.Response.Grading.BatchGradingDetailsResponse;
import com.assookkaa.ClassRecord.Dto.Response.Grading.GradingDetailsResponse;
import com.assookkaa.ClassRecord.Dto.Response.Grading.GradingResponse;
import com.assookkaa.ClassRecord.Service.Grading.GradingService;
import com.assookkaa.ClassRecord.Utils.Token.TokenDecryption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grading")
public class GradingController {

    private final GradingService gradingService;

    public GradingController(GradingService gradingService) {
        this.gradingService = gradingService;
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
//PUT
    @PutMapping("/details/record-score")
    public ResponseEntity<BatchGradingDetailsResponse> recordScore (@RequestHeader("Authorozation")
                                                                    @RequestBody List<GradingDetailRequest> gradingDetailRequests){
      return ResponseEntity.ok(gradingService.recordScore(gradingDetailRequests));
    }

//GET
    @GetMapping("/details/{gradingId}")
    public ResponseEntity<List<GradingDetailsResponse>> details (@RequestHeader("Authorization") String token,
                                                           @PathVariable("gradingId") Integer gradingId) {
        tokenDecryption.tokenDecryption(token);
        List<GradingDetailsResponse> response = gradingService.gradingDetailsResponseList(gradingId);
        return ResponseEntity.ok(response);
    }
}
