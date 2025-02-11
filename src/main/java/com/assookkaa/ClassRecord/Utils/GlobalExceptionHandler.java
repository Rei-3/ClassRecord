package com.assookkaa.ClassRecord.Utils;

import com.assookkaa.ClassRecord.Utils.Error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse>handleApiExceptions(ApiException e){

        ErrorResponse err = new ErrorResponse(e.getStatusCode(),e.getErrorType(),e.getMessage());

        return new ResponseEntity<>(err, HttpStatusCode.valueOf(e.getStatusCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>handleExceptions(Exception e){

        ErrorResponse skibidiError = new ErrorResponse(500, "SKIBIDI_SERVER_RIZZ", "L + Ratio Server Aura");

        return new ResponseEntity<>(skibidiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
