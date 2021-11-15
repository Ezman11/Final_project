package com.web.project.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SuMailCheckValidator implements ConstraintValidator<SuMailCheck, String> {

    @Override
    public void initialize(SuMailCheck constraintAnnotation){}

    private static boolean isSumail(String str){
        return str != null && str.substring(str.indexOf("@")+1).equalsIgnoreCase("silpakorn.edu");
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isSumail(value);
    }
}





