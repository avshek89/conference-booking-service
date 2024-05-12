package com.conference.booking.model;

import com.conference.booking.annotation.TimeConstraintValidator;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotNull;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_EMPTY)
public class TimeSlotRequest {
    @NotNull(message = "Start time is mandatory for booking")
    @TimeConstraintValidator(message = "Please ensure start time should be in 24 hours format")
    private String startTime;
    @NotNull(message = "End time is mandatory for booking")
    @TimeConstraintValidator(message = "Please ensure end time should be in 24 hours format")
    private String endTime;
}
