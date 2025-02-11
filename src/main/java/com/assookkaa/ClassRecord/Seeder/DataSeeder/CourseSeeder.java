package com.assookkaa.ClassRecord.Seeder.DataSeeder;

import com.assookkaa.ClassRecord.Entity.College;
import com.assookkaa.ClassRecord.Entity.Courses;
import com.assookkaa.ClassRecord.Repository.CollegeRepository;
import com.assookkaa.ClassRecord.Repository.CoursesRepository;
import com.assookkaa.ClassRecord.Seeder.Interface.DataSeeder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CourseSeeder implements DataSeeder {

    private final CoursesRepository coursesRepository;
    private final CollegeRepository collegeRepository;

    public CourseSeeder(CoursesRepository coursesRepository, CollegeRepository collegeRepository) {
        this.coursesRepository = coursesRepository;
        this.collegeRepository = collegeRepository;
    }

    @Override
    public void seed() {
        if (coursesRepository.count() == 0) {

            College CAS = collegeRepository.findById(1).orElseThrow(() -> new RuntimeException("No college found"));
            College BosingCollege = collegeRepository.findById(2).orElseThrow(() -> new RuntimeException("No college found"));

            List<Courses> courses = Arrays.asList(
                    new Courses("Bachelor of Science in Information Technology", "BSINT", CAS),
                    new Courses("Bachelor of Science in Computer Science", "BSCS", CAS),
                    new Courses("Bachelor of Science in Vulcanizing", "BSVC", BosingCollege)
            );
            coursesRepository.saveAll(courses);
        }
    }
}
