package com.assookkaa.ClassRecord.Dto.Request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterDto {
    private String fname;
    private String mname;
    private String lname;
    private LocalDate dob;
    private String email;
    private Boolean gender;
    private String Otp;
}
