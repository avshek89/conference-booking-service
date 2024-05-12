package com.conference.booking.services;
import com.conference.booking.model.*;

import java.util.List;

public interface ConferenceRoomBookingService {
    Response<Booking> bookConferenceRoom(BookingRequest bookingRequest);
    Response<AvailableConferenceRoom> getAvailableConferenceRooms(String startTime, String endTime);

}
