package com.cisco.task.caseresource.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CaseNotClosedByIdValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CaseNotClosedById {
    String message() default "Case with id=${validatedValue} is already closed.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

