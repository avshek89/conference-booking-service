package com.conference.booking.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_EMPTY)
public class Booking {
    private ConferenceRoom conferenceRoom;
    private Slot slot;

}

