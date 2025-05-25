package com.assookkaa.ClassRecord.Service.Grading.Interface;

import com.assookkaa.ClassRecord.Dto.Request.Grading.BaseGradeRequest;
import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingDetailRequest;
import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingRequest;
import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingUpdateRequest;
import com.assookkaa.ClassRecord.Dto.Response.Grading.*;
import com.assookkaa.ClassRecord.Dto.Response.Grading.Category.GradesPerCategory;
import com.assookkaa.ClassRecord.Entity.Enrollments;

import java.util.List;

public interface GradingInterface {

    GradingResponse addActivity(GradingRequest gradingRequest);
    UpdatedScoreResponse editRecordedScore(GradingUpdateRequest gradingUpdateRequest);
    BatchGradingDetailsResponse recordScore (List<GradingDetailRequest> gradingDetailRequests);

    //Calculate
    List<GradeComputation> calculateEachTermGrade(Integer teachingLoad, List<Enrollments> enrollments, Integer termId);
    List<SemesterGradeComputation> calculateSemGrade (Integer teachingLoadDetailId, List<Enrollments> enrollments);

    //baseGrade
    BaseGradeResponse editBaseGrade (Integer BaseGradeId, BaseGradeRequest req);
    BaseGradeResponse addBaseGrade ( BaseGradeRequest req);
    BaseGradeResponse getBaseGrade(Integer req);
    //get per cat
    List<GradesPerCategory> getGradesPerCategory(Integer teachingLoadDetailId, Integer termId, Integer categoryId);
    List <GradingDetailsResponse> gradingDetailsResponseList (Integer gradingId);

    //get grading or the number the activities
    List<GradingResponse> getGrading (Integer teachingLoadDetailId, Integer termId, Integer categoryId);
}
