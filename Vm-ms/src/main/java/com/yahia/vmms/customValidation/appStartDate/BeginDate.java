package com.yahia.vmms.customValidation.appStartDate;

import com.yahia.vmms.customValidation.ageValidator.AgeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateBeginValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BeginDate {

    String message() default "voting session start date should be after the current local date time";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
