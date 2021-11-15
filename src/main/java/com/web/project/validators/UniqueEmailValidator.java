package com.web.project.validators;

import javax.validation.*;

import com.web.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UniqueEmail constraintAnnotation){}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        //System.out.println(value);
        if(userRepository == null){
            return true;
        }
        return !userRepository.findByEmail(email).isPresent();
    }
}


