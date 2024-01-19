package com.conference.booking.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
@NoArgsConstructor
@Getter
@ToString
public class BookingRequest {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int numberOfPeople;
}

