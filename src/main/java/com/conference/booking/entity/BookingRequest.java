package com.conference.booking.entity;

import java.time.LocalDateTime;

public class BookingRequest {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int numberOfPeople;

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }
}

