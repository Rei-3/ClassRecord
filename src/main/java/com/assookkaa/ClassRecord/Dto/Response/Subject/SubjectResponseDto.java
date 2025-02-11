package com.assookkaa.ClassRecord.Dto.Response.Subject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubjectResponseDto {
    private Integer id;
    private String subjectDesc;
    private String subjectName;
    private Integer units;
}
