package com.cisco.task.caseresource;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
@Transactional
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

    public Integer createCase(@Valid StartCaseDto startCaseDto){
        Case caseEntity = StartCaseMapper.toEntity(startCaseDto);
        return caseRepository.save(caseEntity).getCaseId();
    }

    //@caseExistsById annotation to validate it before invoking this method (also for testing purposes)
    public CaseDto addNote(NoteDto noteDto, Integer caseId){
        Case caseEntity = caseRepository.getById(caseId);
        Note note = NoteMapper.toEntity(noteDto);
        caseEntity.addNote(note);
        Case savedCaseEntity = caseRepository.save(caseEntity);
        return CaseMapper.toDto(savedCaseEntity);
    }

    // @caseExistsById
    public CaseDto closeCase(Integer caseId){
        Case caseEntity = caseRepository.getById(caseId);
        caseEntity.setStatus(Case.Status.CLOSED);
        Case savedCaseEntity = caseRepository.save(caseEntity);
        return CaseMapper.toDto(savedCaseEntity);
    }
}
