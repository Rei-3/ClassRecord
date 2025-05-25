package com.assookkaa.ClassRecord.Dto.Request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class RegisterDto {
    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[A-Za-z ]{2,50}$", message = "First name must be 2-50 alphabetic characters")
    private String fname;

    @Pattern(regexp = "^[A-Za-z ]{0,50}$", message = "Middle name must be up to 50 alphabetic characters")
    private String mname;

    @NotBlank(message = "Last name is required")
    @Pattern(regexp = "^[A-Za-z ]{2,50}$", message = "Last name must be 2-50 alphabetic characters")
    private String lname;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    @NotBlank(message = "Email is required")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@gmail\\.com$",
            message = "Email must be a valid Google (Gmail) address"
    )
    private String email;

    @NotNull(message = "Gender is required")
    private Boolean gender;

    private String Otp;
}