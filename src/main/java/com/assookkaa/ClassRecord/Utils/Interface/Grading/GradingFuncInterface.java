package com.assookkaa.ClassRecord.Utils.Interface.Grading;

import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingDetailRequest;
import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingRequest;
import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingUpdateRequest;
import com.assookkaa.ClassRecord.Dto.Response.Grading.GradeComputation;
import com.assookkaa.ClassRecord.Dto.Response.Grading.GradingDetailsResponse;
import com.assookkaa.ClassRecord.Dto.Response.Grading.GradingResponse;
import com.assookkaa.ClassRecord.Entity.*;

import java.util.List;
import java.util.Map;

public interface GradingFuncInterface {
    //build
    Grading buildActivity(GradingRequest dto,
                         TeachingLoadDetails teachingLoadDetails,
                         GradeCategory category,
                         Term term
                         );
    //input inside
    GradingDetail inputStudentInTheScoreSheetRecord(GradingDetailRequest dto,
                                                    Grading grading,
                                                    Enrollments enrollments);

    GradingDetail updateScoreOfStudent(GradingUpdateRequest dto);

//    void batchGradingResponse(Grading grading);

    //record
    GradingDetailsResponse gradingDetailResponse(GradingDetail gradingDetail);
    GradingResponse batchGradingResponse(Grading grading);

    //Compute
    Map<Integer, Double> getCategoryWeights(Integer teachingLoadId);
    List<GradingDetail> getFilteredListOfStudents(List <Enrollments> enrollments, Term term);
    GradeComputation computeTermGrades (Enrollments enrollments, List<Grading> gradings, Term term,
                                        Map<Integer, Double> categoryWeights);


}
