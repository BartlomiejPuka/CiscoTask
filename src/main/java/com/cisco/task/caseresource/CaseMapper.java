package com.cisco.task.caseresource;

import lombok.experimental.UtilityClass;

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
        caseDto.setDescription(caseEntity.getDescription());
        caseDto.setSeverity(caseEntity.getSeverity());
        caseDto.setStatus(caseEntity.getStatus());
        caseDto.setTitle(caseEntity.getTitle());
        return caseDto;
    }
}
