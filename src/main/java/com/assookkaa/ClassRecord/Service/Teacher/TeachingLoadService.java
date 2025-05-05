package com.assookkaa.ClassRecord.Service.Teacher;

import com.assookkaa.ClassRecord.Config.Filter.JwtUtil;
import com.assookkaa.ClassRecord.Dto.Response.Subject.SubjectResponseDto;
import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.*;
import com.assookkaa.ClassRecord.Entity.*;
import com.assookkaa.ClassRecord.Repository.*;
import com.assookkaa.ClassRecord.Service.Teacher.Interface.TeachingLoadImplementation;
import com.assookkaa.ClassRecord.Utils.ApiException;
import com.assookkaa.ClassRecord.Utils.Objects.TeachingLoadObject.TeachingLoadFuncs;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class TeachingLoadService implements TeachingLoadImplementation {

    private final JwtUtil jwtUtil;

    private final TeachingLoadRepository teachingLoadRepository;
    private final TeachingLoadDetailsRespository teachingLoadDetailsRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final TeachingLoadFuncs teachingLoadFuncs;
    private final SubjectsRepository subjectsRepository;

    public TeachingLoadService(JwtUtil jwtUtil, TeachingLoadRepository teachingLoadRepository, TeachingLoadDetailsRespository teachingLoadDetailsRepository, EnrollmentRepository enrollmentRepository, TeachingLoadFuncs teachingLoadFuncs, SubjectsRepository subjectsRepository) {
        this.jwtUtil = jwtUtil;
        this.teachingLoadRepository = teachingLoadRepository;
        this.teachingLoadDetailsRepository = teachingLoadDetailsRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.teachingLoadFuncs = teachingLoadFuncs;
        this.subjectsRepository = subjectsRepository;
    }


    @Override
    public TeachingLoadResponseDto addTeachingLoad(String token, TeachingLoadResponseDto teachingLoadResponseDto) {
        String username = jwtUtil.getUsernameFromToken(token);
        Teachers teachers = teachingLoadFuncs.findTeacherByUsername(username);
        Sem sem = teachingLoadFuncs.findSembyId(teachingLoadResponseDto.getSemId());

        TeachingLoad teachingLoad = teachingLoadFuncs.buildTeachingLoad(teachingLoadResponseDto, teachers, sem);
        teachingLoadRepository.save(teachingLoad);

        return teachingLoadFuncs.updateTeachingLoad(teachingLoadResponseDto, teachingLoad, teachers);
    }

    private TeachingLoad findTeachingLoadById(Integer teachingLoadId) {
        return teachingLoadRepository.findById(teachingLoadId)
                .orElseThrow(() -> new ApiException("Teaching Load Not Found", 404, "TEACHING_LOAD_NOT_FOUND"));
    }

    @Override
    public TeachingLoadDetailsResponseDto addTeachingLoadDetails(String token, TeachingLoadDetailsResponseDto teachingLoadDetailsResponseDto) {
        String username = jwtUtil.getUsernameFromToken(token);
        teachingLoadFuncs.findTeacherByUsername(username);
        Subjects subject = teachingLoadFuncs.findSubjectbyId(teachingLoadDetailsResponseDto.getSubjectId());
        TeachingLoad teachingLoad = findTeachingLoadById(teachingLoadDetailsResponseDto.getTeachingLoadId());

        String roomKey = jwtUtil.generateClassroomKeyToken(teachingLoadDetailsResponseDto.getSection(), teachingLoad.getId().toString());
        TeachingLoadDetails teachingLoadDetails = teachingLoadFuncs.buildTeachingLoadDetails(teachingLoadDetailsResponseDto, teachingLoad, subject);
        teachingLoadDetails.setHashKey(roomKey);
        teachingLoadDetailsRepository.save(teachingLoadDetails);

        // Map to response DTO
        return teachingLoadFuncs.updateTeachingLoadDetails(teachingLoadDetailsResponseDto, teachingLoadDetails);
    }

    //GEet
    @Override
    public List<TeachingLoadDetailsListOfStudentsEnrolled> viewAllEnrolledStudents(Integer teachingLoadId) {

        List<Enrollments> enrollments = enrollmentRepository.findByTeachingLoadDetailId(teachingLoadId);

        return enrollments.stream()
                .map(e -> new TeachingLoadDetailsListOfStudentsEnrolled(
                        e.getStudent().getStudentId(),
                        e.getStudent().getStudent().getLname() + ", "
                                + e.getStudent().getStudent().getFname() + " "
                                + (e.getStudent().getStudent().getMname() != null && !e.getStudent().getStudent().getMname().isEmpty()
                                ? e.getStudent().getStudent().getMname().charAt(0) + "." : ""),
                        e.getStudent().getStudent().getGender(),
                        e.getStudent().getStudent().getEmail()
                ))
                .sorted(Comparator.comparing(TeachingLoadDetailsListOfStudentsEnrolled::getStudentId))
                .toList();
    }

    @Override
    public List<TeachingLoadResponseSubjectsDto> getTeachingLoadsForCurrentTeacher(String token) {

        String username = jwtUtil.getUsernameFromToken(token);

        Integer teacherId = teachingLoadFuncs.findTeacherByUsername(username).getId();

        List<TeachingLoad> teachingLoadList = teachingLoadRepository.findByTeachersId(teacherId);

        // Mapping teaching load details to TeachingLoadResponseSubjectsDto
        return teachingLoadList.stream()
                .map(teach -> {

                    List<TeachingLoadDetails> details = teachingLoadDetailsRepository.findByTeachingLoadId(teach.getId());

                    List<TeachingLoadDetailIdOnlyDto> detailDtos = details.stream().map(teachDetails -> {
                        Subjects subject = subjectsRepository.findById(teachDetails.getSubject().getId())
                                .orElseThrow(() -> new ApiException("Subject Not Found", 404, "SUBJECT_NOT_FOUND"));

                        return new TeachingLoadDetailIdOnlyDto(
                                teachDetails.getId(),
                                teachDetails.getHashKey(),
                                teachDetails.getSchedule(),
                                teachDetails.getSection(),
                                new SubjectResponseDto(
                                        subject.getId(),
                                        subject.getSubjectName(),
                                        subject.getSubjectDesc(),
                                        subject.getUnits()
                                )
                        );
                    }).collect(Collectors.toList());

                    return new TeachingLoadResponseSubjectsDto(
                            teach.getId(),
                            teach.getSem().getId(),
                            teach.getStatus(),
                            teach.getAdded_on(),
                            teach.getAcademic_year(),
                            detailDtos
                    );
                })
                .collect(Collectors.toList());
    }

}
