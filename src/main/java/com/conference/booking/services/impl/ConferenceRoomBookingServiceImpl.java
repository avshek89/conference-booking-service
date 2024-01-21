package com.conference.booking.services.impl;

import com.conference.booking.entity.Booking;
import com.conference.booking.entity.ConferenceRoom;
import com.conference.booking.services.ConferenceRoomBookingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
@Slf4j
public class ConferenceRoomBookingServiceImpl implements ConferenceRoomBookingService {
        private List<ConferenceRoom> conferenceRooms;
        private List<Booking> bookings;


    // Create Conference Room
    @PostConstruct
    private void postConstruct() {
        ConferenceRoom amaze = new ConferenceRoom("Amaze", 3, new HashSet<>(Arrays.asList("09:00-09:15", "13:00-13:15", "17:00-17:15")));
        ConferenceRoom beauty = new ConferenceRoom("Beauty", 7, new HashSet<>(Arrays.asList("09:00-09:15", "13:00-13:15", "17:00-17:15")));
        ConferenceRoom inspire = new ConferenceRoom("Inspire", 12, new HashSet<>(Arrays.asList("09:00-09:15", "13:00-13:15", "17:00-17:15")));
        ConferenceRoom strive = new ConferenceRoom("Strive", 20, new HashSet<>(Arrays.asList("09:00-09:15", "13:00-13:15", "17:00-17:15")));
        conferenceRooms = Arrays.asList(amaze, beauty, inspire, strive);
    }

        @Override
        public Booking bookConferenceRoom(LocalDateTime startTime, LocalDateTime endTime, int numberOfPeople) {
            // Check if booking time is within maintenance hours
            if (isDuringMaintenance(startTime, endTime)) {
                throw new IllegalArgumentException("Booking cannot be done during maintenance hours.");
            }
            // Check if booking time is valid (current date, 15-min intervals)
            validateBookingTime(startTime, endTime);
            // Find an available room based on capacity
            ConferenceRoom availableRoom = findAvailableRoom(startTime, endTime, numberOfPeople);
            // Create and add a new booking
            Booking newBooking = new Booking(startTime, endTime, numberOfPeople, availableRoom);
            bookings.add(newBooking);

            return newBooking;
        }

        @Override
        public List<ConferenceRoom> getAvailableConferenceRooms(LocalDateTime startTime, LocalDateTime endTime) {
            // Check if requested time is during maintenance
            if (isDuringMaintenance(startTime, endTime)) {
                throw new IllegalArgumentException("Requested time is during maintenance hours.");
            }

            // Filter available rooms based on existing bookings
            return conferenceRooms.stream()
                    .filter(room -> isRoomAvailable(room, startTime, endTime))
                    .collect(Collectors.toList());
        }

        // Helper methods
        private boolean isDuringMaintenance(LocalDateTime startTime, LocalDateTime endTime) {
            // Check if the booking overlaps with maintenance hours
            for (String maintenanceTiming : conferenceRooms.get(0).getMaintenanceTimings()) {
                String[] timing = maintenanceTiming.split("-");
                LocalDateTime maintenanceStart = LocalDateTime.parse(startTime.toLocalDate() + "T" + timing[0]);
                LocalDateTime maintenanceEnd = LocalDateTime.parse(startTime.toLocalDate() + "T" + timing[1]);
                if (!(endTime.isBefore(maintenanceStart) || startTime.isAfter(maintenanceEnd))) {
                    return true;
                }
            }
            return false;
        }

    private void validateBookingTime(LocalDateTime startTime, LocalDateTime endTime) {
            // Check if booking time is on the current date and in 15-min intervals
            LocalDateTime currentDate = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
            if (!startTime.toLocalDate().equals(currentDate.toLocalDate())) {
                throw new IllegalArgumentException("Booking can only be done for the current date.");
            }

            if (startTime.getMinute() % 15 != 0 || endTime.getMinute() % 15 != 0) {
                throw new IllegalArgumentException("Booking should be in intervals of 15 minutes.");
            }
        }

        private ConferenceRoom findAvailableRoom(LocalDateTime startTime, LocalDateTime endTime, int numberOfPeople) {
            // Find the first available room based on capacity
            for (ConferenceRoom room : conferenceRooms) {
                if (room.getCapacity() >= numberOfPeople && isRoomAvailable(room, startTime, endTime)) {
                    return room;
                }
            }
            throw new IllegalStateException("No available room for the specified time and capacity.");
        }

        private boolean isRoomAvailable(ConferenceRoom room, LocalDateTime startTime, LocalDateTime endTime) {
            // Check if the room is available for the specified time range
            for (Booking booking : bookings) {
                if (booking.getConferenceRoom().equals(room) &&
                        !(endTime.isBefore(booking.getStartTime()) || startTime.isAfter(booking.getEndTime()))) {
                    return false;
                }
            }
            return true;
        }
}

