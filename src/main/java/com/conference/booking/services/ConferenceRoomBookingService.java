package com.conference.booking.services;
import com.conference.booking.entity.Booking;
import com.conference.booking.entity.ConferenceRoom;

import java.time.LocalDateTime;
import java.util.List;

public interface ConferenceRoomBookingService {

    Booking bookConferenceRoom(LocalDateTime startTime, LocalDateTime endTime, int numberOfPeople);
    List<ConferenceRoom> getAvailableConferenceRooms(LocalDateTime startTime, LocalDateTime endTime);
}
