package com.assookkaa.ClassRecord.Controller.GradingComposition;

import com.assookkaa.ClassRecord.Dto.Request.GradingComposition.GradingCompositionDtoRequest;
import com.assookkaa.ClassRecord.Dto.Response.GradingComposition.GradingCompositionDtoResponse;
import com.assookkaa.ClassRecord.Service.Teacher.GradingCompositionService;
import com.assookkaa.ClassRecord.Utils.Token.TokenDecryption;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GradingCompositionController {

    private final GradingCompositionService gradingCompositionService;
    TokenDecryption tokenDecryption = new TokenDecryption();

    public GradingCompositionController(GradingCompositionService gradingCompositionService) {
        this.gradingCompositionService = gradingCompositionService;
    }

    @PostMapping("add-grading-composition")
    public ResponseEntity<GradingCompositionDtoResponse>addGradingComposition(@RequestHeader ("Authorization")String token,
                                                                              @RequestBody GradingCompositionDtoRequest gradingCompositionDtoRequest)
    {
        tokenDecryption.tokenDecryption(token);
        GradingCompositionDtoResponse response = gradingCompositionService.addGradingComposition(tokenDecryption.getToken(), gradingCompositionDtoRequest);
        return ResponseEntity.ok(response);
    }

}
