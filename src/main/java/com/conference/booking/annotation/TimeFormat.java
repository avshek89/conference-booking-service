package com.conference.booking.annotation;

import com.conference.booking.validator.TimeFormatValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TimeFormatValidator.class)
@Documented
public @interface TimeFormat {
    String message() default "Invalid time format. Use HH:mm";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

