package com.yahia.vmms.customValidation.ageValidator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AgeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MinAge {
    String message() default "The candidate must be at least 18 years old";
    int value() default 18;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

