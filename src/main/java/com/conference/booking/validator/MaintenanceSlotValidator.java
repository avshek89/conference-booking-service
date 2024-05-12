package com.conference.booking.validator;

import com.conference.booking.config.BookingConfig;
import com.conference.booking.exception.BookingException;
import com.conference.booking.model.Slot;
import com.conference.booking.model.TimeSlotRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

import static com.conference.booking.constant.ApiConstants.MAINTENANCE_HOURS;
import static com.conference.booking.util.Utils.toLocalTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class MaintenanceSlotValidator implements Validator<TimeSlotRequest> {

    private final BookingConfig bookingConfig;

    @Override
    public void validate(TimeSlotRequest request) {

        LocalTime startTime = toLocalTime(request.getStartTime());
        LocalTime endTime = toLocalTime(request.getEndTime());
        bookingConfig.getMaintenanceSlot().entrySet().stream()
                .filter(entry -> isDuringMaintenance(startTime, endTime, entry.getValue()))
                .findFirst()
                .ifPresent(entry -> {
                    throw new BookingException(MAINTENANCE_HOURS);
                });
    }

    private boolean isDuringMaintenance(LocalTime startTime, LocalTime endTime, Slot slot) {
        LocalTime maintenanceStart = slot.getStartTime();
        LocalTime maintenanceEnd = slot.getEndTime();
        if (!(endTime.isBefore(maintenanceStart) || startTime.isAfter(maintenanceEnd))) {
            return true;
        }
        return false;
    }
}
