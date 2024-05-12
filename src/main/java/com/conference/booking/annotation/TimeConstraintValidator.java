package com.conference.booking.annotation;

import com.conference.booking.validator.TimeConstraintValidatorImpl;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TimeConstraintValidatorImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
public @interface TimeConstraintValidator {

    String message() default "Please ensure time should be in 24 hours format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
