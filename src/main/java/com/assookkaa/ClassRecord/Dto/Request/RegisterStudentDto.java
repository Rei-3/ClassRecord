package com.assookkaa.ClassRecord.Dto.Request;

import lombok.Data;

@Data
public class RegisterStudentDto extends RegisterDto {
    private Integer user_id;
    private Integer studentId;
}
