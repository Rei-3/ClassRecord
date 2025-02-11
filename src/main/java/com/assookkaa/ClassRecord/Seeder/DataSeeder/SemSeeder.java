package com.assookkaa.ClassRecord.Seeder.DataSeeder;

import com.assookkaa.ClassRecord.Entity.Sem;
import com.assookkaa.ClassRecord.Repository.SemRepository;
import com.assookkaa.ClassRecord.Seeder.Interface.DataSeeder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class SemSeeder implements DataSeeder {

    private final SemRepository semRepository;
    public SemSeeder(SemRepository semRepository) {
        this.semRepository = semRepository;
    }

    @Override
    public void seed(){
        if (semRepository.count() == 0){
            List<Sem> sem = Arrays.asList(
                    new Sem("1st Semester"),
                    new Sem("2nd Semester"),
                    new Sem("Summer")
            );

            semRepository.saveAll(sem);
        }
    }
}
