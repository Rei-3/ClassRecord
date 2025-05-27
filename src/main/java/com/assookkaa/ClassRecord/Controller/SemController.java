package com.assookkaa.ClassRecord.Controller;

import com.assookkaa.ClassRecord.Dto.Response.Courses.CoursesResponse;
import com.assookkaa.ClassRecord.Dto.Response.GradingComposition.CategoryDto;
import com.assookkaa.ClassRecord.Dto.Response.Sem.SemResponsedDto;
import com.assookkaa.ClassRecord.Dto.Response.Subject.SubjectDto;
import com.assookkaa.ClassRecord.Dto.Response.Subject.SubjectsWithCourse;
import com.assookkaa.ClassRecord.Dto.Response.Term.TermResponseDto;
import com.assookkaa.ClassRecord.Service.Courses.CoursesService;
import com.assookkaa.ClassRecord.Service.Sem.SemService;
import com.assookkaa.ClassRecord.Service.Subject.SubjectService;
import com.assookkaa.ClassRecord.Service.Term.TermService;
import com.assookkaa.ClassRecord.Utils.Objects.Super;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/show-choices")
public class SemController {

    private final SemService semService;
    private final SubjectService subjectService;
    private final TermService termService;
    private final CoursesService coursesService;

    private final Super usurper;

    public SemController(SemService semService, SubjectService subjectService, TermService termService, CoursesService coursesService, @Qualifier("super") Super usurper){
        this.semService = semService;
        this.subjectService = subjectService;
        this.termService = termService;
        this.coursesService = coursesService;
        this.usurper = usurper;
    }

    @GetMapping("/getSem")
    public List<SemResponsedDto> getAllSem(
            @RequestHeader("API_KEY") String apiKey,
            @RequestHeader("SECRET_KEY") String clientSecretKey
    ) {
        usurper.checkKeys(apiKey, clientSecretKey);
       return semService.getAllSem();
    }

    @GetMapping("/show-subs")
    public List<SubjectDto> getSubjects(
            @RequestHeader("API_KEY") String apiKey,
            @RequestHeader("SECRET_KEY") String clientSecretKey
    ) {
        usurper.checkKeys(apiKey, clientSecretKey);
        return subjectService.getAllSubjects();
    }
    @GetMapping("/get-terms")
    public List <TermResponseDto> getAllTerms(
            @RequestHeader("API_KEY") String apiKey,
            @RequestHeader("SECRET_KEY") String clientSecretKey
    ){
        usurper.checkKeys(apiKey, clientSecretKey);
        return termService.getAllTerms();
    }
    @GetMapping("/categories")
    public List<CategoryDto> getCategories(
            @RequestHeader("API_KEY") String apiKey,
            @RequestHeader("SECRET_KEY") String clientSecretKey
    ){
        usurper.checkKeys(apiKey, clientSecretKey);
        return subjectService.getAllCategories();
    }

    @GetMapping("/courses")
    public List<CoursesResponse> getCourses(
            @RequestHeader("API_KEY") String apiKey,
            @RequestHeader("SECRET_KEY") String clientSecretKey
    ){
        usurper.checkKeys(apiKey, clientSecretKey);
        return coursesService.getAllCourses();
    }

    @GetMapping("/subs-with-courses")
    public List<SubjectsWithCourse> getSubjectsWithCourses(
            @RequestHeader("API_KEY") String apiKey,
            @RequestHeader("SECRET_KEY") String clientSecretKey
    ){
        usurper.checkKeys(apiKey, clientSecretKey);
        return subjectService.getAllSubjectsWithCourse();
    }

    @GetMapping("/courses-with-dep")
    public List<CoursesResponse> getCoursesWithDep(
            @RequestHeader("API_KEY") String apiKey,
            @RequestHeader("SECRET_KEY") String clientSecretKey
    ){
        usurper.checkKeys(apiKey, clientSecretKey);
        return coursesService.getAllCoursesWithCollege();
    }

}
