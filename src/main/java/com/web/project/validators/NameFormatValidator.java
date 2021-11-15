package com.web.project.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameFormatValidator implements ConstraintValidator<NameFormat, String> {

    @Override
    public void initialize(NameFormat constraintAnnotation){}

    private static boolean isNumeric(String str){
        return str != null && str.matches("[^0-9]+") && !str.contains(" ") && str.length()>0 ;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isNumeric(value);
    }
}





