package com.cisco.task.caseresource.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserExistsByIdValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserExistsById {
    String message() default "User with id=${validatedValue} does not exists.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

