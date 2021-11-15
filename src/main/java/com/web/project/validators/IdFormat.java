package com.web.project.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = IdFormatValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD ,ElementType.METHOD })
public @interface IdFormat {
    public String message() default "ID";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default{};
}
