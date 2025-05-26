package com.assookkaa.ClassRecord.Dto.Response.Subject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectsWithCourse {
    private Integer id;
    private String subjectDesc;
    private String subjectName;
    private Integer units;
    private Integer courseId;
    private String courseName;

}
