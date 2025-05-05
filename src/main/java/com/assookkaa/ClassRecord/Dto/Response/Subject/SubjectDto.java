package com.assookkaa.ClassRecord.Dto.Response.Subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDto {
    private Integer id;
    private String subjectDesc;
    private String subjectName;
    private Integer units;
}
