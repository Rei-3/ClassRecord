package com.assookkaa.ClassRecord.Service.Subject;

import com.assookkaa.ClassRecord.Dto.Response.Subject.SubjectResponseDto;
import com.assookkaa.ClassRecord.Entity.Subjects;
import com.assookkaa.ClassRecord.Repository.SubjectsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectsRepository subjectsRepository;

    public List<SubjectResponseDto> getAllSubjects() {
        List<Subjects> subjects = subjectsRepository.findAll();

        return subjects.stream()
                .map(subs -> new SubjectResponseDto(
                        subs.getId(),
                        subs.getSubjectName(),
                        subs.getSubjectDesc(),
                        subs.getUnits()
                ))
                .collect(Collectors.toList());
    }

}
