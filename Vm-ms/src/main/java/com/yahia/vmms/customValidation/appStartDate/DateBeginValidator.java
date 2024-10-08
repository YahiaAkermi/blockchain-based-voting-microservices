package com.yahia.vmms.customValidation.appStartDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class DateBeginValidator implements ConstraintValidator<BeginDate, LocalDateTime> {

    private static final long ALLOWED_TOLERANCE_IN_SECONDS = 60; // Allow a tolerance of 5 seconds

    @Override
    public boolean isValid(LocalDateTime votingSessionStartDate, ConstraintValidatorContext context) {
        LocalDateTime now = LocalDateTime.now();

        // Check if the start date is in the future or within the allowed tolerance
        return isWithinTolerance(votingSessionStartDate, now)||
                votingSessionStartDate.isAfter(now) ||
                votingSessionStartDate.isEqual(now)
                ;
    }

    private boolean isWithinTolerance(LocalDateTime startDate, LocalDateTime now) {
        return Math.abs(java.time.Duration.between(startDate, now).getSeconds()) <= ALLOWED_TOLERANCE_IN_SECONDS;
    }
}
