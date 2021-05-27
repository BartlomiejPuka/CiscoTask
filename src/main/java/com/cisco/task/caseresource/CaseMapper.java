package com.cisco.task.caseresource;

import lombok.experimental.UtilityClass;

import java.util.List;

import static java.util.stream.Collectors.toList;

@UtilityClass
public class CaseMapper {

    public Case toEntity(CaseDto caseDto){
        Case caseEntity = new Case();
        caseEntity.setDescription(caseDto.getDescription());
        caseEntity.setSeverity(caseDto.getSeverity());
        caseEntity.setTitle(caseDto.getTitle());
        caseEntity.setStatus(caseDto.getStatus());
        return caseEntity;
    }
    public CaseDto toDto(Case caseEntity) {
        CaseDto caseDto = new CaseDto();
        caseDto.setId(caseEntity.getCaseId());
        caseDto.setDescription(caseEntity.getDescription());
        caseDto.setSeverity(caseEntity.getSeverity());
        caseDto.setStatus(caseEntity.getStatus());
        caseDto.setTitle(caseEntity.getTitle());
        caseDto.setNotes(extractNotes(caseEntity));
        caseDto.setUser(extractUser(caseEntity));
        return caseDto;
    }

    private String extractUser(Case caseEntity){
        User user = caseEntity.getUser();
        return user.fullName();
    }

    private List<NoteDto> extractNotes(Case caseEntity){
        List<Note> notes = caseEntity.getNotes();
        return notes.stream().map(NoteMapper::toDto).collect(toList());
    }
}
