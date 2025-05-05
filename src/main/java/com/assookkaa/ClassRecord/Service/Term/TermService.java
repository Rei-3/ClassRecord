package com.assookkaa.ClassRecord.Service.Term;

import com.assookkaa.ClassRecord.Dto.Response.Term.TermResponseDto;
import com.assookkaa.ClassRecord.Entity.Term;
import com.assookkaa.ClassRecord.Repository.TermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TermService {

    private final TermRepository termRepository;

    public List<TermResponseDto> getAllTerms(){
        List <Term> terms = termRepository.findAll();

        return terms.stream()
                .map(term -> new TermResponseDto(term.getId(), term.getTerm_type()))
                .collect(Collectors.toList());
    }
}
