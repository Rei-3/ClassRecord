package com.assookkaa.ClassRecord.Utils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApiException extends RuntimeException {
    private int statusCode;
    private String errorType;
    private Map<String, Object> details;

    public ApiException(String message, int statusCode, String errorType) {
        super(message);
        this.statusCode = statusCode;
        this.errorType = errorType;
    }

    public ApiException(String message, int statusCode, String errorType, Map<String, Object> details) {
        super(message);
        this.statusCode = statusCode;
        this.errorType = errorType;
        this.details = details;
    }

    public ApiException(String message, int statusCode, String errorType, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
        this.errorType = errorType;
    }

    public ApiException(String message, int statusCode, String errorType, Map<String, Object> details, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
        this.errorType = errorType;
        this.details = details;
    }
}