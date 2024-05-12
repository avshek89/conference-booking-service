package com.conference.booking.validator;

public interface Validator<T> {
    void validate(T startTime);
}
