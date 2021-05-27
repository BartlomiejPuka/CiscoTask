package com.cisco.task.caseresource.validators;

import com.cisco.task.caseresource.Case;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class StartCaseStatusValidator implements ConstraintValidator<StartCaseStatus, Case.Status> {

    @Override
    public boolean isValid(Case.Status status, ConstraintValidatorContext context) {
        return status == Case.Status.OPEN;
    }
}
