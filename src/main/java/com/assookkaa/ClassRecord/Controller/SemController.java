package com.assookkaa.ClassRecord.Controller;

import com.assookkaa.ClassRecord.Dto.Response.GradingComposition.CategoryDto;
import com.assookkaa.ClassRecord.Dto.Response.Sem.SemResponsedDto;
import com.assookkaa.ClassRecord.Dto.Response.Subject.SubjectDto;
import com.assookkaa.ClassRecord.Dto.Response.Term.TermResponseDto;
import com.assookkaa.ClassRecord.Service.Sem.SemService;
import com.assookkaa.ClassRecord.Service.Subject.SubjectService;
import com.assookkaa.ClassRecord.Service.Term.TermService;
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
    private final TermService termService;

    @GetMapping("/getSem")
    public List<SemResponsedDto> getAllSem() {
       return semService.getAllSem();
    }

    @GetMapping("/show-subs")
    public List<SubjectDto> getSubjects() {
        return subjectService.getAllSubjects();
    }
    @GetMapping("/get-terms")
    public List <TermResponseDto> getAllTerms(){
        return termService.getAllTerms();
    }
    @GetMapping("/categories")
    public List<CategoryDto> getCategories(){
        return subjectService.getAllCategories();
    }
}
