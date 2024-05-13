package com.conference.booking.services;

import com.conference.booking.model.AvailableConferenceRoom;
import com.conference.booking.model.Booking;
import com.conference.booking.model.BookingRequest;
import com.conference.booking.model.Response;

public interface ConferenceRoomBookingService {
    Response<Booking> bookConferenceRoom(BookingRequest bookingRequest);
    Response<AvailableConferenceRoom> getAvailableConferenceRooms(String startTime, String endTime);

}
