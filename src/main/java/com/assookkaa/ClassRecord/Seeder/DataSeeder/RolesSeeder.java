package com.assookkaa.ClassRecord.Seeder.DataSeeder;

import com.assookkaa.ClassRecord.Entity.Roles;
import com.assookkaa.ClassRecord.Repository.RolesRepository;
import com.assookkaa.ClassRecord.Seeder.Interface.DataSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RolesSeeder implements DataSeeder {

    private final RolesRepository rolesRepository;

    public RolesSeeder(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public void seed(){
        if(rolesRepository.count() == 0) {
            List<Roles> roles = Arrays.asList(
                    new Roles("admin"),
                    new Roles("teacher"),
                    new Roles("student")
            );
         rolesRepository.saveAll(roles);
        }
    }

}
