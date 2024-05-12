package com.conference.booking.constant;

public interface ApiConstants {

    String BOOKING_SUCCESS = "Booking is successfully completed";
    String NO_AVAILABLE_ROOMS = "No rooms are available for the requested time slot.";
    String END_AFTER_START = "End time must be after start time.";
    String MAINTENANCE_HOURS = "Requested time is during maintenance hours.";
    String ALLOWED_BOOKING_DURATION = "Booking duration should be 15, 30, or 60 minutes.";
    String ALLOWED_START_TIME = "Start time must be after allowed start time.";
    String ALLOWED_END_TIME = "End time must be before allowed end time.";
    String BOOKING_WINDOW_CLOSED = "The booking window has closed.";
    String TIME_FORMAT = "Time should be in valid format, HH:mm";
    String TECHNICAL = "TECHNICAL";
    String FUNCTIONAL = "FUNCTIONAL";
    String GENERIC = "GENERIC";
}
