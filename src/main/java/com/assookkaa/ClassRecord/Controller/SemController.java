package com.assookkaa.ClassRecord.Controller;

import com.assookkaa.ClassRecord.Dto.Response.Sem.SemResponsedDto;
import com.assookkaa.ClassRecord.Dto.Response.Subject.SubjectResponseDto;
import com.assookkaa.ClassRecord.Service.Sem.SemService;
import com.assookkaa.ClassRecord.Service.Subject.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/show-choices")
@RequiredArgsConstructor
public class SemController {

    private final SemService semService;
    private final SubjectService subjectService;

    @GetMapping("/getSem")
    public List<SemResponsedDto> getAllSem() {
       return semService.getAllSem();
    }

    @GetMapping("/show-subs")
    public List<SubjectResponseDto> getSubjects() {
        return subjectService.getAllSubjects();
    }
}
