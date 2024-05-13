package com.conference.booking.model;

import com.conference.booking.annotation.TimeFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotNull;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Data
@ToString
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_EMPTY)
public class TimeSlotRequest {
    @TimeFormat
    @NotNull(message = "Start time is mandatory for booking")
    private String startTime;
    @TimeFormat
    @NotNull(message = "End time is mandatory for booking")
    private String endTime;
}
