package com.assookkaa.ClassRecord.Dto.Response.TeachingLoad;

import com.assookkaa.ClassRecord.Dto.Response.Subject.SubjectResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeachingLoadDetailIdOnlyDto {
    private Integer id;
    private String key;
    private String schedule;
    private String section;
    private SubjectResponseDto subjects;
}
