package com.cisco.task.caseresource;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StartCaseMapper {

    public Case toEntity(StartCaseDto startCaseDto){
        Case caseEntity = new Case();
        caseEntity.setStatus(startCaseDto.getStatus());
        caseEntity.setTitle(startCaseDto.getTitle());
        caseEntity.setSeverity(startCaseDto.getSeverity());
        caseEntity.setDescription(startCaseDto.getDescription());
        caseEntity.setUser(extractUser(startCaseDto));
        return caseEntity;
    }

    private User extractUser(StartCaseDto startCaseDto){
        User user = new User();
        user.setFirstName(startCaseDto.getUserFirstName());
        user.setLastName(startCaseDto.getUserLastName());
        user.setEmail(startCaseDto.getUserEmail());
        return user;
    }
}

