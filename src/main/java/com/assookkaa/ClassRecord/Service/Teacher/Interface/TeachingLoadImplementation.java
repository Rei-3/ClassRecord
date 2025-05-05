package com.assookkaa.ClassRecord.Service.Teacher.Interface;

import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.TeachingLoadDetailsListOfStudentsEnrolled;
import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.TeachingLoadDetailsResponseDto;
import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.TeachingLoadResponseDto;
import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.TeachingLoadResponseSubjectsDto;
import com.assookkaa.ClassRecord.Entity.TeachingLoad;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface TeachingLoadImplementation {
    TeachingLoadResponseDto addTeachingLoad (String token, TeachingLoadResponseDto teachingLoadResponseDto);
    TeachingLoadDetailsResponseDto addTeachingLoadDetails (String token, TeachingLoadDetailsResponseDto teachingLoadDetailsResponseDto);

    //get
  List<TeachingLoadDetailsListOfStudentsEnrolled> viewAllEnrolledStudents (Integer teachingLoadId);
  List<TeachingLoadResponseSubjectsDto> getTeachingLoadsForCurrentTeacher(String token);

}
