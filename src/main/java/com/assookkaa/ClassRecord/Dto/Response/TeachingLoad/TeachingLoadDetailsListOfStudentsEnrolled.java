package com.assookkaa.ClassRecord.Dto.Response.TeachingLoad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeachingLoadDetailsListOfStudentsEnrolled {
    private Integer enrollmentId;
    private Integer studentId;
    private String name;
    private Boolean gender;
    private String email;
}
