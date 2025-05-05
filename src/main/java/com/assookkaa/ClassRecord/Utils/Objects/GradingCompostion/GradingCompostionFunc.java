package com.assookkaa.ClassRecord.Utils.Objects.GradingCompostion;

import com.assookkaa.ClassRecord.Dto.Request.GradingComposition.GradingCompositionDtoRequest;
import com.assookkaa.ClassRecord.Dto.Response.GradingComposition.GradingCompositionDtoResponse;
import com.assookkaa.ClassRecord.Entity.GradeCategory;
import com.assookkaa.ClassRecord.Entity.GradingComposition;
import com.assookkaa.ClassRecord.Entity.TeachingLoadDetails;
import com.assookkaa.ClassRecord.Repository.*;
import com.assookkaa.ClassRecord.Utils.Interface.GradingComposition.GradingCompositionFuncInterface;
import com.assookkaa.ClassRecord.Utils.Objects.Super;



public class GradingCompostionFunc extends Super implements GradingCompositionFuncInterface {


    public GradingCompostionFunc(TeacherRepository teacherRepository, StudentRepository studentRepository, SubjectsRepository subjectsRepository, SemRepository semRepository, GradeCategoryRepository gradeCategoryRepository, TeachingLoadDetailsRespository teachingLoadDetailsRespository, TermRepository termRepository, EnrollmentRepository enrollmentRepository) {
        super(teacherRepository, studentRepository, subjectsRepository, semRepository, gradeCategoryRepository, teachingLoadDetailsRespository, termRepository, enrollmentRepository);
    }

    @Override
    public GradingComposition buildGradingComposition(GradingCompositionDtoRequest dto, TeachingLoadDetails details, GradeCategory gradeCategory) {
        return GradingComposition.builder()
                .percentage(dto.getPercentage())
                .category(gradeCategory)
                .teachingLoadDetail(details)
                .build();
    }

    @Override
    public GradingCompositionDtoResponse mapToGradingCompositionDtoResponse(GradingComposition gradingComposition) {
        return GradingCompositionDtoResponse.builder()
                .id(gradingComposition.getId())
                .percentage(gradingComposition.getPercentage())
                .categoryId(gradingComposition.getCategory().getId())
                .teachingLoadDetailId(gradingComposition.getTeachingLoadDetail().getId())
                .build();
    }
}
