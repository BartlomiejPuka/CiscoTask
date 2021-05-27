package com.cisco.task.caseresource.validators;

import com.cisco.task.caseresource.Case;
import com.cisco.task.caseresource.CaseRepository;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class CaseNotClosedByIdValidator implements ConstraintValidator<CaseNotClosedById, Integer> {

    CaseRepository caseRepository;

    public CaseNotClosedByIdValidator(CaseRepository caseRepository){
        this.caseRepository = caseRepository;
    }

    @Override
    public boolean isValid(Integer caseId, ConstraintValidatorContext context) {
        return caseRepository.getStatusByCaseId(caseId) != Case.Status.CLOSED;
    }
}