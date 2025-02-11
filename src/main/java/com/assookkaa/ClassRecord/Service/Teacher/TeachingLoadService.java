package com.assookkaa.ClassRecord.Service.Teacher;

import com.assookkaa.ClassRecord.Config.Filter.JwtUtil;
import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.TeachingLoadDetailsResponseDto;
import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.TeachingLoadResponseDto;
import com.assookkaa.ClassRecord.Entity.*;
import com.assookkaa.ClassRecord.Repository.*;
import com.assookkaa.ClassRecord.Utils.ApiException;
import com.assookkaa.ClassRecord.Service.Teacher.Interface.TeachingLoadImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class TeachingLoadService implements TeachingLoadImplementation {

    private final JwtUtil jwtUtil;
    private final TeacherRepository teacherRepository;
    private final SemRepository semRepository;
    private final TeachingLoadRepository teachingLoadRepository;
    private final TeachingLoadDetailsRespository teachingLoadDetailsRepository;
    private final SubjectsRepository subjectsRepository;

    @Override
    public TeachingLoadResponseDto addTeachingLoad(String token, TeachingLoadResponseDto teachingLoadResponseDto) {
        String username = jwtUtil.getUsernameFromToken(token);
        Teachers teachers = findTeacherByUsername(username);
        Sem sem = findSemById(teachingLoadResponseDto.getSemId());

        TeachingLoad teachingLoad = buildTeachingLoad(teachingLoadResponseDto, teachers, sem);
        teachingLoadRepository.save(teachingLoad);

        return updateTeachingLoad(teachingLoadResponseDto, teachingLoad, teachers);
    }

    @Override
    public TeachingLoadDetailsResponseDto addTeachingLoadDetails(String token, TeachingLoadDetailsResponseDto teachingLoadDetailsResponseDto) {
        String username = jwtUtil.getUsernameFromToken(token);
        Teachers teachers = findTeacherByUsername(username);
        Subjects subject = findSubjectById(teachingLoadDetailsResponseDto.getSubjectId());
        TeachingLoad teachingLoad = findTeachingLoadById(teachingLoadDetailsResponseDto.getTeachingLoadId());

        TeachingLoadDetails teachingLoadDetails = buildTeachingLoadDetails(teachingLoadDetailsResponseDto, teachingLoad, subject);
        teachingLoadDetailsRepository.save(teachingLoadDetails);

        // Map to response DTO
        return updateTeachingLoadDetails(teachingLoadDetailsResponseDto, teachingLoadDetails);
    }

    private Teachers findTeacherByUsername(String username) {
        Teachers teacher = teacherRepository.findTeacherByUsername(username);
        if (teacher == null) {
            throw new ApiException("Teacher Not Found", 404, "TEACHER_USER_NOT_FOUND");
        }
        return teacher;
    }

    private Subjects findSubjectById(Integer subjectId) {
        return subjectsRepository.findById(subjectId)
                .orElseThrow(() -> new ApiException("Subject Not Found", 404, "SUBJECT_NOT_FOUND"));
    }

    private Sem findSemById(Integer semId) {
        return semRepository.findById(semId)
                .orElseThrow(() -> new ApiException("Sem Not Found", 404, "SEM_NOT_FOUND"));
    }

    private TeachingLoad findTeachingLoadById(Integer teachingLoadId) {
        return teachingLoadRepository.findById(teachingLoadId)
                .orElseThrow(() -> new ApiException("Teaching Load Not Found", 404, "TEACHING_LOAD_NOT_FOUND"));
    }

    private TeachingLoad buildTeachingLoad(TeachingLoadResponseDto dto, Teachers teacher, Sem sem) {
        return TeachingLoad.builder()
                .added_on(new Date())
                .academic_year(dto.getAcademicYear())
                .status(dto.getStatus())
                .sem(sem)
                .teachers(teacher)
                .details(new ArrayList<>()) // Initialize empty details list
                .build();
    }

    private TeachingLoadDetails buildTeachingLoadDetails(TeachingLoadDetailsResponseDto dto, TeachingLoad teachingLoad, Subjects subjects) {
        return TeachingLoadDetails.builder()
                .teachingLoad(teachingLoad)
                .subject(subjects)
                .section(dto.getSection())
                .schedule(dto.getSchedule())
                .build();
    }

    private TeachingLoadResponseDto updateTeachingLoad(TeachingLoadResponseDto dto, TeachingLoad teachingLoad, Teachers teachers) {
        dto.setTeacherId(teachers.getId());
        dto.setStatus(teachingLoad.getStatus());
        dto.setSemId(teachingLoad.getSem().getId());
        dto.setAcademicYear(teachingLoad.getAcademic_year());
        dto.setAddedOn(teachingLoad.getAdded_on());
        dto.setId(teachingLoad.getId());
        return dto;
    }

    private TeachingLoadDetailsResponseDto updateTeachingLoadDetails(
            TeachingLoadDetailsResponseDto dto, TeachingLoadDetails details
    ) {
        dto.setId(details.getId());
        dto.setTeachingLoadId(details.getTeachingLoad().getId());
        dto.setSection(details.getSection());
        dto.setSubjectId(details.getSubject().getId());
        dto.setSchedule(details.getSchedule());
        return dto;
    }
}
