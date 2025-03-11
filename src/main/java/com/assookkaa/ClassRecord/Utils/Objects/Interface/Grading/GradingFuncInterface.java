package com.assookkaa.ClassRecord.Utils.Objects.Interface.Grading;

import com.assookkaa.ClassRecord.Dto.Request.GradingRequest;
import com.assookkaa.ClassRecord.Entity.Grading;

public interface GradingFuncInterface {
    Grading buildGrading(GradingRequest dto);
}
