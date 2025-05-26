package com.assookkaa.ClassRecord.Service.Subject;

import com.assookkaa.ClassRecord.Config.Filter.JwtUtil;
import com.assookkaa.ClassRecord.Dto.Response.GradingComposition.CategoryDto;
import com.assookkaa.ClassRecord.Dto.Response.GradingComposition.GradingCompositionWithCategoryForTeachingLoadDetailResponse;
import com.assookkaa.ClassRecord.Dto.Response.Subject.SubjectDto;

import com.assookkaa.ClassRecord.Dto.Response.Subject.SubjectsWithCourse;
import com.assookkaa.ClassRecord.Dto.Response.User.StudentUser;
import com.assookkaa.ClassRecord.Dto.Response.User.TeacherUser;
import com.assookkaa.ClassRecord.Entity.*;
import com.assookkaa.ClassRecord.Repository.*;
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
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final CoursesRepository coursesRepository;

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

    public List <SubjectsWithCourse> getAllSubjectsWithCourse() {
        List<Subjects> subjects = subjectsRepository.findAll();

        return subjects.stream()
                .map(subs -> new SubjectsWithCourse(
                        subs.getId(),
                        subs.getSubjectDesc(),
                        subs.getSubjectName(),
                        subs.getUnits(),
                        subs.getCourse().getId(),
                        subs.getCourse().getCourse_name()
                )).collect(Collectors.toList());
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

    public TeacherUser getTeacherUser(String token) {
        String username = jwtUtil.getUsernameFromToken(token);

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }

        Teachers teacherId = teacherRepository.findTeacherByUsername(username);
        if (teacherId == null) {
            throw new IllegalArgumentException("Teacher not found.");
        }

        return TeacherUser.builder()
                .email(user.getEmail())
                .dob(user.getDob().toString())
                .gender(user.getGender())
                .teacherId(teacherId.getTeacherId())
                .build();
    }

    public StudentUser getStudentInfo(String token) {
        String username = jwtUtil.getUsernameFromToken(token);

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }

        Optional<Students> student = studentRepository.findByUsername(username);
        Students studentEntity = student.orElseThrow(() -> new IllegalArgumentException("Student not found."));

        Courses courses = coursesRepository.findByStudentsId(studentEntity.getId());
        if (courses == null) {
            throw new IllegalArgumentException("Course data not found.");
        }

        return StudentUser.builder()
                .email(user.getEmail())
                .dob(user.getDob().toString())
                .gender(user.getGender())
                .course(courses.getCourse_name())
                .build();
    }

}
