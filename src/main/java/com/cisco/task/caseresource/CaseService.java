package com.cisco.task.caseresource;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CaseService {

    private final CaseRepository caseRepository;

    public List<CaseDto> getCaseByUserIdAndStatus(Integer userId, Case.Status status){
        List<Case> cases = caseRepository.getByUserIdAndCaseId(userId, status);
        return cases.stream().map(CaseMapper::toDto).collect(Collectors.toList());
    }

    public List<CaseDto> getCaseByStatus(Case.Status status){
        List<Case> cases = caseRepository.getByStatus(status);
        return cases.stream().map(CaseMapper::toDto).collect(Collectors.toList());
    }

    public List<CaseDto> getCaseByUserId(Integer userId){
        List<Case> cases = caseRepository.getByUserId(userId);
        return cases.stream().map(CaseMapper::toDto).collect(Collectors.toList());
    }
    // @caseExistsById
    public CaseDto getCaseById(Integer caseId){
        Case caseEntity = caseRepository.getById(caseId);
        return CaseMapper.toDto(caseEntity);
    }

    public Integer createCase(CaseDto caseDto){
        Case caseEntity = CaseMapper.toEntity(caseDto);
        return caseRepository.save(caseEntity).getCaseId();
    }

    //@caseExistsById annotation to validate it before invoking this method (also for testing purposes)
    public void addNote(NoteDto noteDto, Integer caseId){
        Case caseEntity = caseRepository.getById(caseId);
        Note note = NoteMapper.toEntity(noteDto);
        note.setCaseId(caseId);
        caseRepository.save(caseEntity);
    }
}
