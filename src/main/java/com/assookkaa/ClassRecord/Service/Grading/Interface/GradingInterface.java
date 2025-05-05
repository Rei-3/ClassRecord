package com.assookkaa.ClassRecord.Service.Grading.Interface;

import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingDetailRequest;
import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingRequest;
import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingUpdateRequest;
import com.assookkaa.ClassRecord.Dto.Response.Grading.*;
import com.assookkaa.ClassRecord.Dto.Response.Grading.Category.GradesPerCategory;
import com.assookkaa.ClassRecord.Entity.Enrollments;
import com.assookkaa.ClassRecord.Entity.Term;

import java.util.List;

public interface GradingInterface {

    GradingResponse addActivity(GradingRequest gradingRequest);
    UpdatedScoreResponse editRecordedScore(GradingUpdateRequest gradingUpdateRequest);
    BatchGradingDetailsResponse recordScore (List<GradingDetailRequest> gradingDetailRequests);

    //Calculate
    List<GradeComputation> calculateEachTermGrade(Integer teachingLoad, List<Enrollments> enrollments, Term termId);
    List<SemesterGradeComputation> calculateSemGrade (Integer teachingLoadDetailId, List<Enrollments> enrollments);

    //get per cat
    GradesPerCategory getGradesPerCategory(Integer teachingLoadDetailId, Integer termId, Integer categoryId);
}
