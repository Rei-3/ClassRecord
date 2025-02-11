package com.assookkaa.ClassRecord.Seeder.DataSeeder;

import com.assookkaa.ClassRecord.Entity.GradeCategory;
import com.assookkaa.ClassRecord.Repository.GradeCategoryRepository;
import com.assookkaa.ClassRecord.Seeder.Interface.DataSeeder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class GradeCategorySeeder implements DataSeeder {

    private final GradeCategoryRepository gradeCategoryRepository;
    public GradeCategorySeeder(GradeCategoryRepository gradeCategoryRepository){
        this.gradeCategoryRepository = gradeCategoryRepository;
    }

    @Override
    public void seed (){
        if(gradeCategoryRepository.count() == 0){
            List<GradeCategory> gradeCategory = Arrays.asList(
                    new GradeCategory("attendance"),
                    new GradeCategory("quiz"),
                    new GradeCategory("exa"),
                    new GradeCategory("activity")
            );
            gradeCategoryRepository.saveAll(gradeCategory);
        }
    }
}
