package com.cisco.task.caseresource;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class CaseExistsByIdValidator implements ConstraintValidator<CaseExistsById, Integer> {

    CaseRepository caseRepository;

    public CaseExistsByIdValidator(CaseRepository caseRepository){
        this.caseRepository = caseRepository;
    }

    @Override
    public boolean isValid(Integer caseId, ConstraintValidatorContext context) {
        return caseRepository.existsById(caseId);
    }
}
