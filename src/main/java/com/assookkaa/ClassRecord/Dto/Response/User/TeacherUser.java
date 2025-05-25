package com.assookkaa.ClassRecord.Dto.Response.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TeacherUser {
    private String email;
    private String dob;
    private Boolean gender;
    private String teacherId;
}
