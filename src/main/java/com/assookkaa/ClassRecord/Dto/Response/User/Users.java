package com.assookkaa.ClassRecord.Dto.Response.User;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Users {
    private int id;
    private String fname;
    private String mname;
    private String lname;
    private boolean gender;
    private String username;
    private String email;
    private LocalDate dob;
    private String otp;
    private String role;
}
