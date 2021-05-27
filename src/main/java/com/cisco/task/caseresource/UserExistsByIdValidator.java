package com.cisco.task.caseresource;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UserExistsByIdValidator implements ConstraintValidator<UserExistsById, Integer> {

    UserRepository userRepository;

    public UserExistsByIdValidator(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(Integer userId, ConstraintValidatorContext context) {
        return userRepository.existsById(userId);
    }
}
