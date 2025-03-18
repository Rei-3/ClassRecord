package com.assookkaa.ClassRecord.Service.Grading.Interface;

import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingDetailRequest;
import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingRequest;
import com.assookkaa.ClassRecord.Dto.Response.Grading.BatchGradingDetailsResponse;
import com.assookkaa.ClassRecord.Dto.Response.Grading.GradingResponse;

import java.util.List;

public interface GradingInterface {

    GradingResponse addActivity(GradingRequest gradingRequest);
    BatchGradingDetailsResponse recordScore(List<GradingDetailRequest> gradingDetailRequests);
    //Calculate


}
