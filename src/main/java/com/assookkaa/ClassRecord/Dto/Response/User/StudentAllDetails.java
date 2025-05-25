package com.assookkaa.ClassRecord.Dto.Response.User;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import lombok.experimental.SuperBuilder;



@Data
@SuperBuilder
public class StudentAllDetails extends Users{
    private String studentId;
    private String courseName;
}
