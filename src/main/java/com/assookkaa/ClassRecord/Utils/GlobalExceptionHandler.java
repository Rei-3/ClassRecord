package com.assookkaa.ClassRecord.Utils;

import com.assookkaa.ClassRecord.Utils.Error.*;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Custom API exceptions
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiExceptions(ApiException e) {
        return buildErrorResponse(e.getStatusCode(), e.getErrorType(), e.getMessage(), null);
    }

    // Validation errors (DTO validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField(),
                        fieldError -> fieldError.getDefaultMessage()
                ));
        return buildErrorResponse(400, "VALIDATION_FAILED", "Invalid request data", errors);
    }

    // Path variable/request param validation errors
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException e) {
        Map<String, String> errors = e.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        violation -> violation.getMessage()
                ));
        return buildErrorResponse(400, "CONSTRAINT_VIOLATION", "Invalid parameter values", errors);
    }

    // Type mismatch (e.g., string instead of number)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        String message = String.format("Parameter '%s' should be of type %s",
                e.getName(),
                e.getRequiredType() != null ? e.getRequiredType().getSimpleName() : "unknown");
        return buildErrorResponse(400, "TYPE_MISMATCH", message, null);
    }

    // Missing required parameters
    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            MissingServletRequestPartException.class
    })
    public ResponseEntity<ErrorResponse> handleMissingParams(Exception e) {
        String paramName = e instanceof MissingServletRequestParameterException
                ? ((MissingServletRequestParameterException)e).getParameterName()
                : ((MissingServletRequestPartException)e).getRequestPartName();
        return buildErrorResponse(400, "MISSING_PARAMETER",
                "Required parameter '" + paramName + "' is missing", null);
    }

    // Invalid JSON format
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJson(HttpMessageNotReadableException e) {
        return buildErrorResponse(400, "INVALID_JSON", "Malformed JSON request", null);
    }

    // Unsupported media type
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedMediaType(HttpMediaTypeNotSupportedException e) {
        return buildErrorResponse(415, "UNSUPPORTED_MEDIA_TYPE",
                "Content type '" + e.getContentType() + "' is not supported", null);
    }

    // Method not allowed
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e) {
        return buildErrorResponse(405, "METHOD_NOT_ALLOWED",
                "HTTP method '" + e.getMethod() + "' is not supported for this endpoint", null);
    }

    // File upload size exceeded
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxUploadSizeExceeded(MaxUploadSizeExceededException e) {
        return buildErrorResponse(413, "FILE_TOO_LARGE",
                "Uploaded file size exceeds maximum allowed size", null);
    }

    // Database access errors
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException e) {
        return buildErrorResponse(500, "DATABASE_ERROR",
                "An error occurred while accessing the database", null);
    }

    // Authorization errors
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException e) {
        return buildErrorResponse(403, "ACCESS_DENIED",
                "You don't have permission to access this resource", null);
    }

    // All other exceptions (with your fun twist)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception e) {
        Map<String, Object> details = new HashMap<>();
        details.put("debugMessage", e.getMessage());
        details.put("errorType", e.getClass().getSimpleName());

        return buildErrorResponse(500, "SKIBIDI_SERVER_RIZZ",
                "L + Ratio Server Aura 🎵 Skibidi dop dop yes yes 🎵", details);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(
            int statusCode, String errorType, String message, Map<String, ?> details) {
        ErrorResponse err = new ErrorResponse(statusCode, errorType, message, (Map<String, Object>) details);
        return ResponseEntity.status(statusCode).body(err);
    }
}