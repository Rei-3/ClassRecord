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

    public TeachingLoadService(TeacherRepository teacherRepository, StudentRepository studentRepository, SubjectsRepository subjectsRepository, SemRepository semRepository, GradeCategoryRepository gradeCategoryRepository, TeachingLoadDetailsRespository teachingLoadDetailsRespository, JwtUtil jwtUtil, TeachingLoadRepository teachingLoadRepository, TeachingLoadDetailsRespository teachingLoadDetailsRepository) {
        super(teacherRepository, studentRepository, subjectsRepository, semRepository, gradeCategoryRepository, teachingLoadDetailsRespository);
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



//    private TeachingLoad buildTeachingLoad(TeachingLoadResponseDto dto, Teachers teacher, Sem sem) {
//        return TeachingLoad.builder()
//                .added_on(new Date())
//                .academic_year(dto.getAcademicYear())
//                .status(dto.getStatus())
//                .sem(sem)
//                .teachers(teacher)
//                .details(new ArrayList<>()) // Initialize empty details list
//                .build();
//    }
//
//    private TeachingLoadDetails buildTeachingLoadDetails(TeachingLoadDetailsResponseDto dto, TeachingLoad teachingLoad, Subjects subjects) {
//        return TeachingLoadDetails.builder()
//                .teachingLoad(teachingLoad)
//                .subject(subjects)
//                .section(dto.getSection())
//                .schedule(dto.getSchedule())
//                .build();
//    }

//    private TeachingLoadResponseDto updateTeachingLoad(TeachingLoadResponseDto dto, TeachingLoad teachingLoad, Teachers teachers) {
//        dto.setTeacherId(teachers.getId());
//        dto.setStatus(teachingLoad.getStatus());
//        dto.setSemId(teachingLoad.getSem().getId());
//        dto.setAcademicYear(teachingLoad.getAcademic_year());
//        dto.setAddedOn(teachingLoad.getAdded_on());
//        dto.setId(teachingLoad.getId());
//        return dto;
//    }

//    private TeachingLoadDetailsResponseDto updateTeachingLoadDetails(
//            TeachingLoadDetailsResponseDto dto, TeachingLoadDetails details
//    ) {
//        dto.setId(details.getId());
//        dto.setTeachingLoadId(details.getTeachingLoad().getId());
//        dto.setSection(details.getSection());
//        dto.setSubjectId(details.getSubject().getId());
//        dto.setSchedule(details.getSchedule());
//        return dto;
//    }
}
