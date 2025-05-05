package com.assookkaa.ClassRecord.Service.Subject;

import com.assookkaa.ClassRecord.Dto.Response.GradingComposition.CategoryDto;
import com.assookkaa.ClassRecord.Dto.Response.GradingComposition.GradingCompositionWithCategoryForTeachingLoadDetailResponse;
import com.assookkaa.ClassRecord.Dto.Response.Subject.SubjectDto;

import com.assookkaa.ClassRecord.Entity.GradeCategory;
import com.assookkaa.ClassRecord.Entity.GradingComposition;
import com.assookkaa.ClassRecord.Entity.Subjects;
import com.assookkaa.ClassRecord.Repository.GradeCategoryRepository;
import com.assookkaa.ClassRecord.Repository.GradingCompositionRepository;
import com.assookkaa.ClassRecord.Repository.SubjectsRepository;
import com.assookkaa.ClassRecord.Service.Subject.Interface.SubjectInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectService implements SubjectInterface {

    private final SubjectsRepository subjectsRepository;
    private final GradingCompositionRepository gradingCompositionRepository;
    private final GradeCategoryRepository gradeCategoryRepository;

    public List<SubjectDto> getAllSubjects() {
        List<Subjects> subjects = subjectsRepository.findAll();

        return subjects.stream()
                .map(subs -> new SubjectDto(
                        subs.getId(),
                        subs.getSubjectName(),
                        subs.getSubjectDesc(),
                        subs.getUnits()
                ))
                .collect(Collectors.toList());
    }

    public List<CategoryDto> getAllCategories() {
        List<GradeCategory> cats = gradeCategoryRepository.findAll();

        return cats.stream()
                .map(cat-> new CategoryDto(
                        cat.getId(),
                        cat.getCategoryName()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public GradingCompositionWithCategoryForTeachingLoadDetailResponse getGradingCompositionWithCategoryForTeachingLoadDetail(Integer teachingLoadDetailId) {

        //first find teaching load detail id in grading comp
        List<GradingComposition> teachingDetail = gradingCompositionRepository.findByTeachingLoadDetailId(teachingLoadDetailId);
        if (teachingDetail.isEmpty()){
            throw new RuntimeException("teaching load detail not found");
        }
        //2nd stream grading comp with the teach id
        List<GradingCompositionWithCategoryForTeachingLoadDetailResponse.CompositionItem> resp =
                teachingDetail.stream().map(gc-> new GradingCompositionWithCategoryForTeachingLoadDetailResponse.CompositionItem(
                        gc.getId(),
                        gc.getPercentage(),
                        new GradingCompositionWithCategoryForTeachingLoadDetailResponse.CatDto(
                                gc.getCategory().getId(),
                                gc.getCategory().getCategoryName()
                        )
                ))
                        .toList();

        return new GradingCompositionWithCategoryForTeachingLoadDetailResponse(
            teachingLoadDetailId, resp
        );
    }
}
