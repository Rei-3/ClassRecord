package com.assookkaa.ClassRecord.Service.Teacher;

import com.assookkaa.ClassRecord.Config.Filter.JwtUtil;
import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.TeachingLoadDetailsResponseDto;
import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.TeachingLoadResponseDto;
import com.assookkaa.ClassRecord.Entity.*;
import com.assookkaa.ClassRecord.Repository.*;
import com.assookkaa.ClassRecord.Service.Teacher.Interface.TeachingLoadImplementation;
import com.assookkaa.ClassRecord.Utils.ApiException;
import com.assookkaa.ClassRecord.Utils.Objects.TeachingLoadObject.TeachingLoadFuncs;
import org.springframework.stereotype.Service;

@Service


public class TeachingLoadService extends TeachingLoadFuncs implements TeachingLoadImplementation {

    private final JwtUtil jwtUtil;

    private final TeachingLoadRepository teachingLoadRepository;
    private final TeachingLoadDetailsRespository teachingLoadDetailsRepository;

    public TeachingLoadService(TeacherRepository teacherRepository, StudentRepository studentRepository, SubjectsRepository subjectsRepository, SemRepository semRepository, GradeCategoryRepository gradeCategoryRepository, TeachingLoadDetailsRespository teachingLoadDetailsRespository, TermRepository termRepository, JwtUtil jwtUtil, TeachingLoadRepository teachingLoadRepository, TeachingLoadDetailsRespository teachingLoadDetailsRepository) {
        super(teacherRepository, studentRepository, subjectsRepository, semRepository, gradeCategoryRepository, teachingLoadDetailsRespository, termRepository);
        this.jwtUtil = jwtUtil;
        this.teachingLoadRepository = teachingLoadRepository;
        this.teachingLoadDetailsRepository = teachingLoadDetailsRepository;
    }


    @Override
    public TeachingLoadResponseDto addTeachingLoad(String token, TeachingLoadResponseDto teachingLoadResponseDto) {
        String username = jwtUtil.getUsernameFromToken(token);
        Teachers teachers = findTeacherByUsername(username);
        Sem sem = findSembyId(teachingLoadResponseDto.getSemId());

        TeachingLoad teachingLoad = buildTeachingLoad(teachingLoadResponseDto, teachers, sem);
        teachingLoadRepository.save(teachingLoad);

        return updateTeachingLoad(teachingLoadResponseDto, teachingLoad, teachers);
    }

    private TeachingLoad findTeachingLoadById(Integer teachingLoadId) {
        return teachingLoadRepository.findById(teachingLoadId)
                .orElseThrow(() -> new ApiException("Teaching Load Not Found", 404, "TEACHING_LOAD_NOT_FOUND"));
    }

    @Override
    public TeachingLoadDetailsResponseDto addTeachingLoadDetails(String token, TeachingLoadDetailsResponseDto teachingLoadDetailsResponseDto) {
        String username = jwtUtil.getUsernameFromToken(token);
        findTeacherByUsername(username);
        Subjects subject = findSubjectbyId(teachingLoadDetailsResponseDto.getSubjectId());
        TeachingLoad teachingLoad = findTeachingLoadById(teachingLoadDetailsResponseDto.getTeachingLoadId());

        String roomKey = jwtUtil.generateClassroomKeyToken(teachingLoadDetailsResponseDto.getSection(), teachingLoad.getId().toString());
        TeachingLoadDetails teachingLoadDetails = buildTeachingLoadDetails(teachingLoadDetailsResponseDto, teachingLoad, subject);
        teachingLoadDetails.setHashKey(roomKey);
        teachingLoadDetailsRepository.save(teachingLoadDetails);

        // Map to response DTO
        return updateTeachingLoadDetails(teachingLoadDetailsResponseDto, teachingLoadDetails);
    }

}
