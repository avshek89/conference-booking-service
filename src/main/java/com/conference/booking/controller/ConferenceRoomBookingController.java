package com.conference.booking.controller;

import com.conference.booking.entity.Booking;
import com.conference.booking.entity.BookingRequest;
import com.conference.booking.entity.ConferenceRoom;
import com.conference.booking.services.ConferenceRoomBookingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/conference-room")
public class ConferenceRoomBookingController {
    private final ConferenceRoomBookingService conferenceRoomBookingService;

    public ConferenceRoomBookingController(ConferenceRoomBookingService conferenceRoomBookingService) {
        this.conferenceRoomBookingService = conferenceRoomBookingService;
    }

    @PostMapping("/book")
    public ResponseEntity<Booking> bookConferenceRoom(@RequestBody BookingRequest bookingRequest) {
        // Implement logic to handle booking request
        return null;
    }

    @GetMapping("/available")
    public ResponseEntity<List<ConferenceRoom>> getAvailableConferenceRooms(
            @RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
        // Implement logic to get available conference rooms
        return null;
    }
}

