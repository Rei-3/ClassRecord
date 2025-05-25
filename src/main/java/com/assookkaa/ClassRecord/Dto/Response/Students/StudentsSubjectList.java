package com.assookkaa.ClassRecord.Dto.Response.Students;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class StudentsSubjectList {
    private Integer teachingLoadDetailId;
    private String subjectDesc;
    private String subjectName;
    private String academicYear;
    private String semName;
    private String teacher;
    private String section;
    private Boolean status;
    private String schedule;
}
