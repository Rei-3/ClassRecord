package com.assookkaa.ClassRecord.Utils;

import lombok.Data;
import java.time.Instant;
import java.util.Map;

@Data
public class ErrorResponse {
    private int statusCode;
    private String errorType;
    private String message;
    private Instant timestamp;
    private String path;
    private Map<String, Object> details;

    public ErrorResponse(int statusCode, String errorType, String message) {
        this(statusCode, errorType, message, null);
    }

    public ErrorResponse(int statusCode, String errorType, String message, Map<String, Object> details) {
        this.statusCode = statusCode;
        this.errorType = errorType;
        this.message = message;
        this.timestamp = Instant.now();
        this.details = details;
    }
}