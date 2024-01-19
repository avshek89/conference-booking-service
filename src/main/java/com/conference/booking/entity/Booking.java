package com.conference.booking.entity;

import java.time.LocalDateTime;

import java.time.LocalDateTime;

public class Booking {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int numberOfPeople;
    private ConferenceRoom conferenceRoom;

    public Booking(LocalDateTime startTime, LocalDateTime endTime, int numberOfPeople, ConferenceRoom conferenceRoom) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.numberOfPeople = numberOfPeople;
        this.conferenceRoom = conferenceRoom;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public ConferenceRoom getConferenceRoom() {
        return conferenceRoom;
    }
}

