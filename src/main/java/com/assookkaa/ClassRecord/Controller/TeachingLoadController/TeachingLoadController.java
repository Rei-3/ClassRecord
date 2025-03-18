package com.assookkaa.ClassRecord.Controller.TeachingLoadController;

import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.TeachingLoadDetailsResponseDto;
import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.TeachingLoadResponseDto;
import com.assookkaa.ClassRecord.Service.Teacher.TeachingLoadService;
import com.assookkaa.ClassRecord.Utils.Token.TokenDecryption;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeachingLoadController {

    private final TeachingLoadService teachingLoadService;

    public TeachingLoadController(TeachingLoadService teachingLoadService) {
        this.teachingLoadService = teachingLoadService;
    }

    TokenDecryption tokenDecryption = new TokenDecryption();

    @PostMapping("/api/add-teaching-load")
    public ResponseEntity<TeachingLoadResponseDto> teachingLoad(@RequestHeader("Authorization") String token,
                                                @RequestBody  TeachingLoadResponseDto teachingLoadResponseDto) {

        tokenDecryption.tokenDecryption(token);

        TeachingLoadResponseDto responseDto = teachingLoadService.addTeachingLoad(tokenDecryption.getToken(), teachingLoadResponseDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/api/add-teaching-load-details")
    public ResponseEntity<TeachingLoadDetailsResponseDto> teachingLoadDetails(
            @RequestHeader ("Authorization") String token,
            @RequestBody  TeachingLoadDetailsResponseDto teachingLoadDetailsResponseDto
    ){

        tokenDecryption.tokenDecryption(token);

        TeachingLoadDetailsResponseDto responseDto = teachingLoadService.addTeachingLoadDetails(tokenDecryption.getToken(), teachingLoadDetailsResponseDto);
        return ResponseEntity.ok(responseDto);
    }

}
