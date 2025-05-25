package com.assookkaa.ClassRecord.Seeder.DataSeeder;

import com.assookkaa.ClassRecord.Entity.Term;
import com.assookkaa.ClassRecord.Repository.TermRepository;
import com.assookkaa.ClassRecord.Seeder.Interface.DataSeeder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class TermSeeder implements DataSeeder {

    private final TermRepository termRepository;
    public TermSeeder(TermRepository termRepository) {
        this.termRepository = termRepository;
    }

    @Override
    public void seed() {
        if (termRepository.count() == 0) {
            List<Term> term = Arrays.asList(
                    new Term(1,"Midterm"),
                    new Term(2, "Final")
            );
            termRepository.saveAll(term);
        }

    }
}
