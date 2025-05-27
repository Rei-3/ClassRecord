package com.assookkaa.ClassRecord.Controller;

import com.assookkaa.ClassRecord.Dto.Response.GradingComposition.GradingCompositionWithCategoryForTeachingLoadDetailResponse;
import com.assookkaa.ClassRecord.Service.Subject.SubjectService;
import com.assookkaa.ClassRecord.Utils.Objects.Super;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/getSub")
public class SubjectController {

    private final SubjectService subjectService;
    private final Super usurper;

    public SubjectController(SubjectService subjectService, @Qualifier("super") Super usurper) {
        this.subjectService = subjectService;
        this.usurper = usurper;
    }

    @GetMapping
    public ResponseEntity<String> getSubject() {
        return ResponseEntity.ok("Hello World");
    }

    @GetMapping("/grading-composition/{teachingLoadDetailId}")
    public ResponseEntity<GradingCompositionWithCategoryForTeachingLoadDetailResponse> getGradingComposition (
            @RequestHeader("Authorization") String token, @PathVariable Integer teachingLoadDetailId,
            @RequestHeader("API_KEY") String apiKey,
            @RequestHeader("SECRET_KEY") String clientSecretKey
    ){
        usurper.checkKeys(apiKey, clientSecretKey);
        GradingCompositionWithCategoryForTeachingLoadDetailResponse resp =
                subjectService.getGradingCompositionWithCategoryForTeachingLoadDetail(teachingLoadDetailId);

        return ResponseEntity.ok(resp);
    }


}
