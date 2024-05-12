package com.conference.booking.controller;

import com.conference.booking.model.*;
import com.conference.booking.services.ConferenceRoomBookingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/conference-room")
public class BookingController {
    private final ConferenceRoomBookingService conferenceRoomBookingService;

    public BookingController(ConferenceRoomBookingService conferenceRoomBookingService) {
        this.conferenceRoomBookingService = conferenceRoomBookingService;
    }

    // API to book the available room
    @PostMapping("/booking")
    public ResponseEntity<Response<Booking>> bookConferenceRoom(@RequestBody BookingRequest request) {
        return ResponseEntity.ok(conferenceRoomBookingService.bookConferenceRoom(request));
    }

    // API to get the available room details
    @GetMapping("/available-rooms")
    public ResponseEntity<Response<AvailableConferenceRoom>> getAvailableConferenceRooms(
            @RequestParam String startTime, @RequestParam String endTime) {
        return ResponseEntity.ok(conferenceRoomBookingService.getAvailableConferenceRooms(startTime,endTime));
    }
}

