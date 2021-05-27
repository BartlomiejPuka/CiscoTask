package com.cisco.task.caseresource;

import com.cisco.task.caseresource.validators.CaseExistsById;
import com.cisco.task.caseresource.validators.CaseNotClosedById;
import com.cisco.task.caseresource.validators.UserExistsById;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Validated
public class CaseService {

    private final CaseRepository caseRepository;
    private final UserRepository userRepository;

    public List<CaseDto> getCaseByUserIdAndStatus(@UserExistsById Integer userId, Case.Status status){
        List<Case> cases = caseRepository.getByUserIdAndStatus(userId, status);
        return cases.stream().map(CaseMapper::toDto).collect(Collectors.toList());
    }

    public List<CaseDto> getCaseByStatus(Case.Status status){
        List<Case> cases = caseRepository.getByStatus(status);
        return cases.stream().map(CaseMapper::toDto).collect(Collectors.toList());
    }

    public List<CaseDto> getCaseByUserId(@UserExistsById Integer userId){
        List<Case> cases = caseRepository.getByUserId(userId);
        return cases.stream().map(CaseMapper::toDto).collect(Collectors.toList());
    }

    public CaseDto getCaseById(@CaseExistsById Integer caseId){
        Case caseEntity = caseRepository.getById(caseId);
        return CaseMapper.toDto(caseEntity);
    }

    public CaseDto createCase(@Valid StartCaseDto startCaseDto){
        Case caseEntity = StartCaseMapper.toEntity(startCaseDto);
        User user = StartCaseMapper.extractUser(startCaseDto);
        User storedUser = userRepository.findByEmail(user.getEmail());
        caseEntity.setUser(Optional.ofNullable(storedUser).orElse(user));
        caseEntity.setNotes(new ArrayList<>());
        Case savedCaseEntity = caseRepository.save(caseEntity);
        return CaseMapper.toDto(savedCaseEntity);
    }

    public CaseDto addNote(NoteDto noteDto, @CaseExistsById @CaseNotClosedById Integer caseId){
        Case caseEntity = caseRepository.getById(caseId);
        Note note = NoteMapper.toEntity(noteDto);
        caseEntity.addNote(note);
        Case savedCaseEntity = caseRepository.save(caseEntity);
        return CaseMapper.toDto(savedCaseEntity);
    }

    public CaseDto closeCase(@CaseExistsById @CaseNotClosedById Integer caseId){
        Case caseEntity = caseRepository.getById(caseId);
        caseEntity.setStatus(Case.Status.CLOSED);
        Case savedCaseEntity = caseRepository.save(caseEntity);
        return CaseMapper.toDto(savedCaseEntity);
    }
}
