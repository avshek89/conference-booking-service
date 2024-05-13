package com.conference.booking.validator;
import com.conference.booking.annotation.TimeFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class TimeFormatValidator implements ConstraintValidator<TimeFormat, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        try {
            LocalTime.parse(value);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}

