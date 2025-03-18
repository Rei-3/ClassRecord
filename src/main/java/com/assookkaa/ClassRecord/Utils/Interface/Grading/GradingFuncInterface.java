package com.assookkaa.ClassRecord.Utils.Interface.Grading;

import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingDetailRequest;
import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingRequest;
import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingUpdateRequest;
import com.assookkaa.ClassRecord.Dto.Response.Grading.GradingDetailsResponse;
import com.assookkaa.ClassRecord.Dto.Response.Grading.GradingResponse;
import com.assookkaa.ClassRecord.Entity.*;

import java.util.List;

public interface GradingFuncInterface {
    Grading buildActivity(GradingRequest dto,
                         TeachingLoadDetails teachingLoadDetails,
                         GradeCategory category,
                         Term term
                         );

    GradingDetail inputStudentInTheScoreSheetRecord(GradingDetailRequest dto,
                                                    Grading grading,
                                                    Enrollments enrollments);

    GradingDetail updateScoreOfStudent(GradingUpdateRequest dto);

    GradingResponse batchGradingResponse(Grading grading);
    GradingDetailsResponse gradingDetailResponse(GradingDetail gradingDetail);
    GradingResponse batchGradingResponse(Grading grading, List<GradingDetailsResponse> detailsResponses);
}
