package com.web.project.validators;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = UniqueIDValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD ,ElementType.METHOD })
public @interface UniqueID {
    public String message() default "There is already user with this ID!";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default{};
}
