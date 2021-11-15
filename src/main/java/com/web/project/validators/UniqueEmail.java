package com.web.project.validators;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = UniqueEmailValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD ,ElementType.METHOD })
public @interface UniqueEmail {
    public String message() default "There is already user with this email!";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default{};
}
