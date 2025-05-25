package com.assookkaa.ClassRecord.Service.Sem;

import com.assookkaa.ClassRecord.Dto.Response.Sem.SemResponsedDto;
import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.AllTeachingLoadResponse;
import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.TLD;
import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.TeachingLoadDetailsResponseDto;
import com.assookkaa.ClassRecord.Entity.Enrollments;
import com.assookkaa.ClassRecord.Entity.Sem;
import com.assookkaa.ClassRecord.Entity.TeachingLoad;
import com.assookkaa.ClassRecord.Entity.TeachingLoadDetails;
import com.assookkaa.ClassRecord.Repository.EnrollmentRepository;
import com.assookkaa.ClassRecord.Repository.SemRepository;
import com.assookkaa.ClassRecord.Repository.TeachingLoadDetailsRespository;
import com.assookkaa.ClassRecord.Repository.TeachingLoadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SemService {

    private final SemRepository semRepository;
    private final TeachingLoadRepository teachingLoadRepository;
    private final TeachingLoadDetailsRespository teachingLoadDetailsRespository;
    private final EnrollmentRepository enrollmentRepository;

    public List<SemResponsedDto> getAllSem(){
        List<Sem> semList = semRepository.findAll();

        return semList.stream()
                .map(sems -> new SemResponsedDto(sems.getId(), sems.getSem_name()))
                .collect(Collectors.toList());
    }

    public List <AllTeachingLoadResponse> getAllTeachingLoad(){
        List<TeachingLoad> teachingLoadList = teachingLoadRepository.findAll();

        return teachingLoadList.stream()
                .map(t -> new AllTeachingLoadResponse(
                        t.getId(),
                        t.getSem().getSem_name(),
                        t.getStatus(),
                        t.getTeachers().getTeacher().getFname() + " " + t.getTeachers().getTeacher().getFname(),
                        t.getAdded_on(),
                        t.getAcademic_year()))
                .collect(Collectors.toList());

    }

    public List <TeachingLoadDetailsResponseDto> getAllTeachingLoadDetails(){
        List< TeachingLoadDetails >teachingLoadDetails = teachingLoadDetailsRespository.findAll();

        return teachingLoadDetails.stream()
                .map(
                        tld -> new TeachingLoadDetailsResponseDto(
                                tld.getId(),
                                tld.getTeachingLoad().getId())
                ).collect(Collectors.toList());

    }

    public List<TLD> getTLDbyTLiD(int id){
        List <TeachingLoadDetails> tld = teachingLoadDetailsRespository.findByTeachingLoadId(id);
         if (tld.isEmpty()) {
             return new ArrayList<>();
         }



         return tld.stream()
                 .map(
                         td-> new TLD(
                                 td.getId(),
                                 td.getEnrollments().size(),
                                 td.getTeachingLoad().getId(),
                                 td.getSubject().getSubjectDesc() + " - " + td.getSubject().getSubjectName(),
                                 td.getSchedule(),
                                 td.getHashKey(),
                                 td.getSection()
                         )
                 ).toList();

    }
}
