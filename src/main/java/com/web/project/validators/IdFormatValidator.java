package com.web.project.validators;

import javax.validation.*;

public class IdFormatValidator implements ConstraintValidator<IdFormat, String> {

    @Override
    public void initialize(IdFormat constraintAnnotation){}

    private static boolean isNumeric(String str){
        return str != null && str.matches("[0-9]+") && str.length()>7;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isNumeric(value);
    }
}





