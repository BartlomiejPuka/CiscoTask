package com.cisco.task.caseresource.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StartCaseStatusValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StartCaseStatus {
    String message() default "Status of new case should be set to OPEN.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

