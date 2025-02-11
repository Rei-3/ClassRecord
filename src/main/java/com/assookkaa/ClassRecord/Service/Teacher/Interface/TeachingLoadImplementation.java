package com.assookkaa.ClassRecord.Service.Teacher.Interface;

import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.TeachingLoadDetailsResponseDto;
import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.TeachingLoadResponseDto;

public interface TeachingLoadImplementation {
    TeachingLoadResponseDto addTeachingLoad (String token, TeachingLoadResponseDto teachingLoadResponseDto);
    TeachingLoadDetailsResponseDto addTeachingLoadDetails (String token, TeachingLoadDetailsResponseDto teachingLoadDetailsResponseDto);
}
