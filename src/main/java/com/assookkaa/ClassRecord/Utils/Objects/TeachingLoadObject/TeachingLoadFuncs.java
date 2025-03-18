package com.assookkaa.ClassRecord.Utils.Objects.TeachingLoadObject;

import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.TeachingLoadDetailsResponseDto;
import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.TeachingLoadResponseDto;
import com.assookkaa.ClassRecord.Entity.*;
import com.assookkaa.ClassRecord.Repository.*;
import com.assookkaa.ClassRecord.Utils.Interface.TeachingLoad.TeachingLoadInterface;
import com.assookkaa.ClassRecord.Utils.Objects.Super;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Date;


public class TeachingLoadFuncs extends Super implements TeachingLoadInterface {


    public TeachingLoadFuncs(TeacherRepository teacherRepository, StudentRepository studentRepository, SubjectsRepository subjectsRepository, SemRepository semRepository, GradeCategoryRepository gradeCategoryRepository, TeachingLoadDetailsRespository teachingLoadDetailsRespository, TermRepository termRepository) {
        super(teacherRepository, studentRepository, subjectsRepository, semRepository, gradeCategoryRepository, teachingLoadDetailsRespository, termRepository);
    }

    @Override
    public TeachingLoad buildTeachingLoad(TeachingLoadResponseDto dto, Teachers teacher, Sem sem) {
        return TeachingLoad.builder()
                .added_on(new Date())
                .academic_year(dto.getAcademicYear())
                .status(dto.getStatus())
                .sem(sem)
                .teachers(teacher)
                .details(new ArrayList<>()) // Initialize empty details list
                .build();
    }

    @Override
    public TeachingLoadDetails buildTeachingLoadDetails(TeachingLoadDetailsResponseDto dto, TeachingLoad teachingLoad, Subjects subjects) {
        return TeachingLoadDetails.builder()
                .teachingLoad(teachingLoad)
                .subject(subjects)
                .section(dto.getSection())
                .schedule(dto.getSchedule())
                .build();
    }

    @Override
    public TeachingLoadResponseDto updateTeachingLoad(TeachingLoadResponseDto dto, TeachingLoad teachingLoad, Teachers teachers) {
        dto.setTeacherId(teachers.getId());
        dto.setStatus(teachingLoad.getStatus());
        dto.setSemId(teachingLoad.getSem().getId());
        dto.setAcademicYear(teachingLoad.getAcademic_year());
        dto.setAddedOn(teachingLoad.getAdded_on());
        dto.setId(teachingLoad.getId());
        return dto;
    }

    @Override
    public TeachingLoadDetailsResponseDto updateTeachingLoadDetails(TeachingLoadDetailsResponseDto dto, TeachingLoadDetails details) {
        dto.setId(details.getId());
        dto.setTeachingLoadId(details.getTeachingLoad().getId());
        dto.setSection(details.getSection());
        dto.setKey(details.getHashKey());
        dto.setSubjectId(details.getSubject().getId());
        dto.setSchedule(details.getSchedule());
        return dto;
    }
}
