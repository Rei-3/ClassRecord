package com.assookkaa.ClassRecord.Dto.Request;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterTeacherDto extends RegisterDto {
    private Integer user_id;
    @Pattern(regexp = "^[A-Z0-9]+$", message = "Only uppercase letters and numbers allowed")
    private String teacher_id;
}
