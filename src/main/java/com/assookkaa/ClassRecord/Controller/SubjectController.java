package com.assookkaa.ClassRecord.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/getSub")
public class SubjectController {
    @GetMapping
    public ResponseEntity<String> getSubject() {
        return ResponseEntity.ok("Hello World");
    }
}
