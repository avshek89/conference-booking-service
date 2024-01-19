package com.conference.booking.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Booking {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int numberOfPeople;
    private ConferenceRoom conferenceRoom;
}

