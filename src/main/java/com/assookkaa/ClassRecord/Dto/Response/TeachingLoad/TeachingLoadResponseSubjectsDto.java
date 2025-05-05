package com.assookkaa.ClassRecord.Dto.Response.TeachingLoad;

import com.assookkaa.ClassRecord.Dto.Response.Subject.SubjectResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeachingLoadResponseSubjectsDto {
    private Integer id;
    private Integer semId;
    private Boolean status;
    private Date addedOn;
    private String academicYear;
    private List <TeachingLoadDetailIdOnlyDto> teachingLoadId;
}
