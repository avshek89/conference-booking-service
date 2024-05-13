package com.conference.booking.services.impl;

import com.conference.booking.constant.ApiConstants;
import com.conference.booking.entity.ConferenceBookingEntity;
import com.conference.booking.entity.ConferenceRoomEntity;
import com.conference.booking.model.*;
import com.conference.booking.repository.BookingRepository;
import com.conference.booking.repository.ConferenceRoomRepository;
import com.conference.booking.services.ConferenceRoomBookingService;
import com.conference.booking.validator.BookingTimeValidator;
import com.conference.booking.validator.MaintenanceSlotValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import static com.conference.booking.constant.ApiConstants.BOOKING_SUCCESS;
import static com.conference.booking.constant.ApiConstants.FUNCTIONAL;
import static com.conference.booking.util.Utils.*;

@Service
@AllArgsConstructor
@Slf4j
public class ConferenceRoomBookingServiceImpl implements ConferenceRoomBookingService {
        private final BookingRepository rookingRepository;
        private final ConferenceRoomRepository conferenceRoomRepository;
        private final MaintenanceSlotValidator maintenanceSlotValidator;
        private final BookingTimeValidator bookingTimeValidator;


    @Override
    public Response<Booking> bookConferenceRoom(BookingRequest bookingRequest) {
        log.info("bookConferenceRoom >> initiated {} ");
        validateBookingRequest(bookingRequest);
        List<ConferenceBookingEntity> allBookingEntity = rookingRepository.findBookedRoomByTime(toSqlTime(bookingRequest.getStartTime()),
                toSqlTime(bookingRequest.getEndTime()));
        List<Booking> bookings = buildBookings(allBookingEntity);
        List<ConferenceRoom> allConferenceRooms = retrieveAllRooms();
        Optional<ConferenceRoom> conferenceRoom = findAvailableRoom(bookings, bookingRequest.getNumberOfParticipants(), allConferenceRooms);
        if (conferenceRoom.isEmpty()) {
            return buildErrorResponse(ApiConstants.NO_AVAILABLE_ROOMS, FUNCTIONAL);
        }
        ConferenceRoomEntity roomEntity = getAvailableRoomEntity(conferenceRoom.get()).orElse(null);
        ConferenceBookingEntity conferenceBookingEntity = mapToEntity(bookingRequest, roomEntity);
        rookingRepository.save(conferenceBookingEntity);
        log.info("bookConferenceRoom >> completed {} ");
        return buildSuccessResponse(buildBookingResponse(conferenceBookingEntity), BOOKING_SUCCESS);
    }

    @Override
    public Response<AvailableConferenceRoom> getAvailableConferenceRooms(String start, String end) {
        validateBookingTime(TimeSlotRequest.builder().startTime(start).endTime(end).build());
        List<ConferenceBookingEntity> allBookingEntity = rookingRepository.findBookedRoomByTime(toSqlTime(start),
                toSqlTime(end));
        List<Booking> bookings = buildBookings(allBookingEntity);
        List<ConferenceRoom> allConferenceRooms = retrieveAllRooms();
        List<ConferenceRoom> availableConferenceRoom = findAvailableRooms(bookings, allConferenceRooms);
        if (availableConferenceRoom.isEmpty()) {
            return buildErrorResponse(ApiConstants.NO_AVAILABLE_ROOMS, FUNCTIONAL);
        }
        AvailableConferenceRoom roomAvailabilityResponse = buildRoomAvailabilityResponse(allConferenceRooms);
        return buildSuccessResponse(roomAvailabilityResponse);
    }

    private void validateBookingRequest(BookingRequest bookingRequest) {
        log.info("validateBookingRequest >> bookingRequest {} ", bookingRequest);
        validateBookingTime(bookingRequest);
    }

    private void validateBookingTime(TimeSlotRequest timeSlotRequest) {
        bookingTimeValidator.validate(timeSlotRequest);
        maintenanceSlotValidator.validate(timeSlotRequest);
    }

    private List<ConferenceRoom> retrieveAllRooms() {
        List<ConferenceRoomEntity> rooms = fetchAllRooms();
        return buildRooms(rooms);
    }

    @Cacheable
    private List<ConferenceRoomEntity> fetchAllRooms() {
        return conferenceRoomRepository.findAllByOrderByCapacityAsc();
    }

    private Optional<ConferenceRoomEntity> getAvailableRoomEntity(ConferenceRoom conferenceRoom) {
        return fetchAllRooms().stream()
                .filter(roomEntity -> roomEntity.getRoomId() == conferenceRoom.getRoomNumber())
                .findFirst();
    }
}

