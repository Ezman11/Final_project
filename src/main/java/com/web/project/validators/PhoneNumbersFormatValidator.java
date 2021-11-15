package com.web.project.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumbersFormatValidator implements ConstraintValidator<PhoneNumbersFormat, String> {

    @Override
    public void initialize(PhoneNumbersFormat constraintAnnotation){}

    private static boolean isNumeric(String str){
        return str != null && str.matches("[0-9]+") && str.length()>9;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isNumeric(value);
    }
}





