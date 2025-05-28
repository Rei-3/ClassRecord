package com.assookkaa.ClassRecord;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ClassRecordApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClassRecordApplication.class, args);
	}
}
