package com.assookkaa.ClassRecord.Controller;

import com.assookkaa.ClassRecord.Dto.Response.GradingComposition.GradingCompositionWithCategoryForTeachingLoadDetailResponse;
import com.assookkaa.ClassRecord.Service.Subject.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/getSub")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public ResponseEntity<String> getSubject() {
        return ResponseEntity.ok("Hello World");
    }

    @GetMapping("/grading-composition/{teachingLoadDetailId}")
    public ResponseEntity<GradingCompositionWithCategoryForTeachingLoadDetailResponse> getGradingComposition (
            @RequestHeader("Authorization") String token, @PathVariable Integer teachingLoadDetailId
    ){
        GradingCompositionWithCategoryForTeachingLoadDetailResponse resp =
                subjectService.getGradingCompositionWithCategoryForTeachingLoadDetail(teachingLoadDetailId);

        return ResponseEntity.ok(resp);
    }


}
