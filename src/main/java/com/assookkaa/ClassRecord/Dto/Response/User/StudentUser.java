package com.assookkaa.ClassRecord.Dto.Response.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class StudentUser{
    private String email;
    private String dob;
    private Boolean gender;
    private String course;
}
