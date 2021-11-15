package com.web.project.validators;

import javax.validation.*;

import com.web.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueIDValidator implements ConstraintValidator<UniqueID, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize (UniqueID uniqueID){}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //System.out.println(value);
        if(userRepository == null){
            return true;
        }
        //System.out.println(userRepository.findById(value).isPresent()+"aaa"); //or .isEmpty() in java11
        return !userRepository.findById(value).isPresent();
    }
}

