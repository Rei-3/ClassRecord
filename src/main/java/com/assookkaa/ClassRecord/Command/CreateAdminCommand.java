package com.assookkaa.ClassRecord.Command;

import com.assookkaa.ClassRecord.Service.AdminSetupOnRunOfTheAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CreateAdminCommand implements CommandLineRunner {

    @Autowired
    private AdminSetupOnRunOfTheAppService createAdmin;

    public void run(String... args)throws Exception{
        if(args.length > 0 && "summon-admin".equals(args[0])) {
            createAdmin.SummonAdmin();
        }
    }
}
