package com.conference.booking.controller;

import com.conference.booking.entity.Booking;
import com.conference.booking.entity.BookingRequest;
import com.conference.booking.entity.ConferenceRoom;
import com.conference.booking.services.ConferenceRoomBookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Booking> bookConferenceRoom(@RequestBody BookingRequest request) {
        Booking booking =  conferenceRoomBookingService.bookConferenceRoom(request.getStartTime(),request.getEndTime(),request.getNumberOfPeople());
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @GetMapping("/available")
    public ResponseEntity<List<ConferenceRoom>> getAvailableConferenceRooms(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime endTime) {
        List<ConferenceRoom> availableBooking =  conferenceRoomBookingService.getAvailableConferenceRooms(startTime,endTime);
        return new ResponseEntity<>(availableBooking, HttpStatus.OK);
    }
}

