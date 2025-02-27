package com.assookkaa.ClassRecord.Dto.Response.TeachingLoad;

import lombok.Data;

@Data
public class TeachingLoadDetailsResponseDto {
    private Integer id;
    private Integer teachingLoadId;
    private Integer subjectId;
    private String schedule;
    private String key;
    private String section;
}
