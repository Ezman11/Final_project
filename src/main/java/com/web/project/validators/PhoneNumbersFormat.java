package com.web.project.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PhoneNumbersFormatValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD ,ElementType.METHOD })
public @interface PhoneNumbersFormat {
    public String message() default "not Phone numbers";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default{};
}
