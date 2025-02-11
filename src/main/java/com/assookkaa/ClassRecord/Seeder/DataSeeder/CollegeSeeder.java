package com.assookkaa.ClassRecord.Seeder.DataSeeder;

import com.assookkaa.ClassRecord.Entity.College;
import com.assookkaa.ClassRecord.Repository.CollegeRepository;
import com.assookkaa.ClassRecord.Seeder.Interface.DataSeeder;
import org.springframework.stereotype.Component;


import java.util.Arrays;
import java.util.List;

@Component
public class CollegeSeeder implements DataSeeder {

    private final CollegeRepository collegeRepository;
    public CollegeSeeder(CollegeRepository collegeRepository){
        this.collegeRepository = collegeRepository;
    }

    @Override
    public void seed(){
        if(collegeRepository.count() == 0){
            List<College> colleges = Arrays.asList(
                    new College("College of Arts and Sciences", "CAS"),
                    new College("College of Bossing", "BS")
            );

            collegeRepository.saveAll(colleges);

        }
    }

}
