package com.cisco.task.caseresource;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CaseExistsByIdValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CaseExistsById {
    String message() default "Case with id=${validatedValue} does not exists.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
