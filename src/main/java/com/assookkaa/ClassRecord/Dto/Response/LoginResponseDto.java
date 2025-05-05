package com.assookkaa.ClassRecord.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {
    private String username;
    private String token;
    private String refreshToken;
}
