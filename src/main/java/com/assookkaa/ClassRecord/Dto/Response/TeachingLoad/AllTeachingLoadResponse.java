package com.assookkaa.ClassRecord.Dto.Response.TeachingLoad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllTeachingLoadResponse {
    private Integer id;
    private String semName;
    private Boolean status;
    private String teacherName;
    private Date addedOn;
    private String academicYear;
}
