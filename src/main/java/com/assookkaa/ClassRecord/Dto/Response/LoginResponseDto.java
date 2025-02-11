package com.assookkaa.ClassRecord.Dto.Response;

import lombok.Data;

@Data
public class LoginResponseDto {
    private String username;
    private String token;

    public LoginResponseDto(String username, String token) {
        this.username = username;
        this.token = token;
    }
}
