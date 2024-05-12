package com.conference.booking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@NoArgsConstructor
@Getter
@ToString
public class BookingRequest extends  TimeSlotRequest{
    @Min(value = 1, message = "Reservation for the conference room necessitates the presence of one or more individuals")
    @Max(value = 20, message = "Unfortunately, we must inform you that there are no available rooms to accommodate the specified number of individuals at this time")
    private int numberOfParticipants;
}

