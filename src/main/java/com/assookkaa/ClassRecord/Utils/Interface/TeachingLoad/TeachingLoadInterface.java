package com.assookkaa.ClassRecord.Utils.Interface.TeachingLoad;

import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.TeachingLoadDetailsResponseDto;
import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.TeachingLoadResponseDto;
import com.assookkaa.ClassRecord.Entity.*;

public interface TeachingLoadInterface {
    TeachingLoad buildTeachingLoad(TeachingLoadResponseDto dto, Teachers teacher, Sem sem);
    TeachingLoadDetails buildTeachingLoadDetails(TeachingLoadDetailsResponseDto dto, TeachingLoad teachingLoad, Subjects subjects);
    TeachingLoadResponseDto updateTeachingLoad(TeachingLoadResponseDto dto, TeachingLoad teachingLoad, Teachers teachers);
    TeachingLoadDetailsResponseDto updateTeachingLoadDetails(TeachingLoadDetailsResponseDto dto, TeachingLoadDetails details);
}
