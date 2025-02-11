package com.assookkaa.ClassRecord.Dto.Response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterResponseDto {
    private Integer id;
    private String fname;
    private String mname;
    private String lname;
    private LocalDate dob;
    private String email;
    private Boolean gender;
}
