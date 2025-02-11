package com.assookkaa.ClassRecord.Dto.Request;

import lombok.Data;

@Data
public class RegisterTeacherDto extends RegisterDto {
    private Integer user_id;
    private String teacher_id;
}
