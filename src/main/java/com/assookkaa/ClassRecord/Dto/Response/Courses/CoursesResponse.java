package com.assookkaa.ClassRecord.Dto.Response.Courses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CoursesResponse {
    private Integer id;
    private String courseCode;
    private String courseName;
    private String department;

    public CoursesResponse(Integer id, String courseCode, String courseName) {
        this.id = id;
        this.courseCode = courseCode;
        this.courseName = courseName;
    }
}
