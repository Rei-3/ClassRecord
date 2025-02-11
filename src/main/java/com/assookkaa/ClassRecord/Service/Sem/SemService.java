package com.assookkaa.ClassRecord.Service.Sem;

import com.assookkaa.ClassRecord.Dto.Response.Sem.SemResponsedDto;
import com.assookkaa.ClassRecord.Entity.Sem;
import com.assookkaa.ClassRecord.Repository.SemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SemService {

    private final SemRepository semRepository;

    public List<SemResponsedDto> getAllSem(){
        List<Sem> semList = semRepository.findAll();

        return semList.stream()
                .map(sems -> new SemResponsedDto(sems.getId(), sems.getSem_name()))
                .collect(Collectors.toList());
    }
}
