package com.assookkaa.ClassRecord.Dto.Response;

import lombok.Data;

@Data
public class RegisterTeacherResponseDto extends RegisterResponseDto{
    private Integer id;
    private Integer user_id;
    private String teacher_id;
}
