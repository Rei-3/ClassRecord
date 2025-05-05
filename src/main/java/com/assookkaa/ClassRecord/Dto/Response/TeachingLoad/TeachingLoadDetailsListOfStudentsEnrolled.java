package com.assookkaa.ClassRecord.Dto.Response.TeachingLoad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeachingLoadDetailsListOfStudentsEnrolled {
    private Integer studentId;
    private String name;
    private Boolean gender;
    private String email;
}
