package com.conference.booking.validator;

import com.conference.booking.config.BookingConfig;
import com.conference.booking.exception.BookingException;
import com.conference.booking.model.TimeSlotRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.conference.booking.constant.ApiConstants.*;
import static com.conference.booking.util.Utils.currentTime;
import static com.conference.booking.util.Utils.toLocalTime;


@Slf4j
@Component
@RequiredArgsConstructor
public class BookingTimeValidator implements Validator<TimeSlotRequest> {

    private final BookingConfig bookingConfig;
    @Override
    public void validate(TimeSlotRequest request) {
        LocalTime startTime = toLocalTime(request.getStartTime());
        LocalTime endTime = toLocalTime(request.getEndTime());

        // Booking window is closed
        if (startTime.isBefore(toLocalTime(currentTime()))) {
            throw new BookingException(BOOKING_WINDOW_CLOSED);
        }
        // Ensure that the duration is either 15, 30, or 60 minutes
        if (endTime.isBefore(startTime)) {
            throw new BookingException(END_AFTER_START);
        }
        long durationInMinutes = java.time.Duration.between(startTime, endTime).toMinutes();
        // Ensure that the duration is either 15, 30, or 60 minutes
        if (durationInMinutes != bookingConfig.getHourlyDuration() && durationInMinutes != bookingConfig.getHalfHourlyDuration() && durationInMinutes != bookingConfig.getHourlyDuration()) {
            throw new BookingException(ALLOWED_BOOKING_DURATION);
        }
        // Start time validation
        if (bookingConfig.getMinimumStartTime().isAfter(startTime)) {
            throw new BookingException(ALLOWED_START_TIME);
        }
        // End time validation
        if (bookingConfig.getMaximumEndTime().isBefore(endTime)) {
            throw new BookingException(ALLOWED_END_TIME);
        }
    }
}
