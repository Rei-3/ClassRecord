package com.assookkaa.ClassRecord.Seeder;

import com.assookkaa.ClassRecord.Seeder.Interface.DataSeeder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MasterSeeder implements CommandLineRunner {
    private final List<DataSeeder> seeders;

    public MasterSeeder(List<DataSeeder> seeders) {
        this.seeders = seeders;
    }

    public void run (String... args) throws Exception {
        seeders.forEach(DataSeeder::seed);
    }
}
