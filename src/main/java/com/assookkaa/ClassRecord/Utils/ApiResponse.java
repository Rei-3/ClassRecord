package com.assookkaa.ClassRecord.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class ApiResponse <T>{
    private String message;
    private T data;
}
