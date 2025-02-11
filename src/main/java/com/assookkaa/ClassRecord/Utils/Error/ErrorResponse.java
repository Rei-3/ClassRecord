package com.assookkaa.ClassRecord.Utils.Error;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorResponse {
    private Integer code;
    private String errorType;
    private String errorMessage;
}
