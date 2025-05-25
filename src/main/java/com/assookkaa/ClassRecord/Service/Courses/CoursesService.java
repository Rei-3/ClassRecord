package com.assookkaa.ClassRecord.Service.Courses;

import com.assookkaa.ClassRecord.Dto.Response.Courses.CoursesResponse;
import com.assookkaa.ClassRecord.Entity.College;
import com.assookkaa.ClassRecord.Entity.Courses;
import com.assookkaa.ClassRecord.Repository.CollegeRepository;
import com.assookkaa.ClassRecord.Repository.CoursesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoursesService {

    private final CoursesRepository coursesRepository;
    private final CollegeRepository collegeRepository;

    public CoursesService(CoursesRepository coursesRepository, CollegeRepository collegeRepository) {
        this.coursesRepository = coursesRepository;
        this.collegeRepository = collegeRepository;
    }

    public List<CoursesResponse> getAllCourses() {
        List<Courses> courses = coursesRepository.findAll();

        return courses.stream()
                .map(courses1 -> new CoursesResponse(
                        courses1.getId(),
                        courses1.getCourse_code(),
                        courses1.getCourse_name()

                ))
                .toList();
    }

    public List<CoursesResponse> getAllCoursesWithCollege() {
        List<Courses> courses = coursesRepository.findAll();

        College college = collegeRepository.findByCoursesId(courses.get(0).getId());

        return courses.stream()
                .map(courses1 -> new CoursesResponse(
                        courses1.getId(),
                        courses1.getCourse_code(),
                        courses1.getCourse_name(),
                        courses1.getCollege().getCollege_code() + " - " + courses1.getCollege().getCollege_name()
                ))
                .toList();
    }

}
