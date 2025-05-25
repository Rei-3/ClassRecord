package com.assookkaa.ClassRecord.Dto.Request;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UsernameAndPasswordDto {
    private Integer courseId;
    private String username;
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$",
            message = "Password must be 8-20 characters with at least: 1 uppercase, 1 lowercase, 1 number, and 1 special character (@#$%^&+=!)"
    )
    private String password;
}
