package com.assookkaa.ClassRecord.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/hello-world")
    public String helloworld (){
        return new String("Hello World");
    }

}
