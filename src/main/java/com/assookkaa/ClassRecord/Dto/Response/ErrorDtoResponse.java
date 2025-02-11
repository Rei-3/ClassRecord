package com.assookkaa.ClassRecord.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDtoResponse {
    private int statusCode;
    private String message;
    private String errorType;
}
