package com.conference.booking.services.impl;

import com.conference.booking.constant.ApiConstants;
import com.conference.booking.entity.ConferenceBookingEntity;
import com.conference.booking.entity.ConferenceRoomEntity;
import com.conference.booking.model.*;
import com.conference.booking.repository.BookingRepository;
import com.conference.booking.repository.ConferenceRoomRepository;
import com.conference.booking.services.ConferenceRoomBookingService;
import com.conference.booking.util.Utils;
import com.conference.booking.validator.BookingTimeValidator;
import com.conference.booking.validator.MaintenanceSlotValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static com.conference.booking.constant.ApiConstants.BOOKING_SUCCESS;
import static com.conference.booking.constant.ApiConstants.FUNCTIONAL;
import static com.conference.booking.enums.ResponseStatus.SUCCESS;
import static com.conference.booking.util.Utils.*;
import static java.util.Collections.emptyList;
import static org.springframework.util.CollectionUtils.isEmpty;

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

            validateBookingRequest(bookingRequest);
            List<ConferenceBookingEntity> allBookingEntity = rookingRepository.findBookedRoomByTime(toSqlTime(bookingRequest.getStartTime()),
                    toSqlTime(bookingRequest.getEndTime()));
            List<Booking> bookings = buildReservations(allBookingEntity);
            List<ConferenceRoom> allConferenceRooms = retrieveAllRooms();
            Optional<ConferenceRoom> conferenceRoom = findAvailableRoom(bookings, bookingRequest.getNumberOfParticipants(), allConferenceRooms);
            if (conferenceRoom.isEmpty()) {
                return buildErrorResponse(ApiConstants.NO_AVAILABLE_ROOMS,FUNCTIONAL);
            }
            ConferenceRoomEntity roomEntity = getAvailableRoomEntity(conferenceRoom.get()).orElse(null);
            ConferenceBookingEntity conferenceBookingEntity = mapToEntity(bookingRequest, roomEntity);
            rookingRepository.save(conferenceBookingEntity);
            log.info("Booking successfully completed");
            return buildSuccessResponse(buildBookingResponse(conferenceBookingEntity), BOOKING_SUCCESS);
        }

    @Override
    public Response<AvailableConferenceRoom> getAvailableConferenceRooms(String start, String end) {
        validateBookingTime(TimeSlotRequest.builder().startTime(start).endTime(end).build());
        List<ConferenceBookingEntity> allBookingEntity = rookingRepository.findBookedRoomByTime(toSqlTime(start),
                toSqlTime(end));
        List<Booking> bookings = buildReservations(allBookingEntity);
        List<ConferenceRoom> allConferenceRooms = retrieveAllRooms();
        List<ConferenceRoom> availableConferenceRoom = findAvailableRooms(bookings, allConferenceRooms);
        if (availableConferenceRoom.isEmpty()) {
            return buildErrorResponse(ApiConstants.NO_AVAILABLE_ROOMS,FUNCTIONAL);
        }
        AvailableConferenceRoom roomAvailabilityResponse = buildRoomAvailabilityResponse(allConferenceRooms);
        return buildSuccessResponse(roomAvailabilityResponse);
    }

    public static AvailableConferenceRoom buildRoomAvailabilityResponse(List<ConferenceRoom> rooms) {
        return AvailableConferenceRoom.builder()
                .availableConferenceRooms(rooms)
                .build();
    }

    public static Optional<ConferenceRoom> findAvailableRoom(List<Booking> bookings,
                     int numberOfParticipants, List<ConferenceRoom> rooms) {
        if (isEmpty(bookings)) {
            return isRoomAvailable(numberOfParticipants, rooms);
        }

        List<ConferenceRoom> bookedRooms = findBookedRooms(bookings);
        rooms.removeAll(bookedRooms);
        return isRoomAvailable(numberOfParticipants, rooms);
    }
    private static Optional<ConferenceRoom> isRoomAvailable(int numberOfParticipants, List<ConferenceRoom> rooms) {
        return rooms.stream()
                .filter(room -> numberOfParticipants <= room.getCapacity())
                .findFirst();
    }

   private void validateBookingRequest(BookingRequest bookingRequest) {
       log.info("validateBookingRequest >> bookingRequest {} ",bookingRequest);
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
    public static List<ConferenceRoom> buildRooms(List<ConferenceRoomEntity> rooms) {
        return rooms.stream()
                .map(Utils::buildRoom)
                .collect(Collectors.toList());
    }

  /*  private static ConferenceRoom buildRoom(String room) {
        return ConferenceRoom.builder()
                .name(room)
                .build();
    }*/

    public static List<Booking> buildReservations(List<ConferenceBookingEntity> reservations) {
        if (isEmpty(reservations)) {
            return emptyList();
        }

        return reservations.stream()
                .map(Utils::buildReservation)
                .collect(Collectors.toList());
    }

    public static List<ConferenceRoom> findAvailableRooms(List<Booking> bookings, List<ConferenceRoom> rooms) {
        if (isEmpty(bookings)) {
            return rooms;
        }

        List<ConferenceRoom> reservedRooms = findBookedRooms(bookings);
        rooms.removeAll(reservedRooms);
        return rooms;
    }
    private static List<ConferenceRoom> findBookedRooms(List<Booking> bookings) {
        return bookings.stream()
                .map(Booking::getConferenceRoom)
                .distinct()
                .collect(Collectors.toList());
    }
    private Optional<ConferenceRoomEntity> getAvailableRoomEntity(ConferenceRoom conferenceRoom) {
        return fetchAllRooms().stream()
                .filter(roomEntity -> roomEntity.getRoomId() == conferenceRoom.getRoomNumber())
                .findFirst();
    }
    public static Booking buildBookingResponse(ConferenceBookingEntity bookingEntity) {
        return Booking.builder()
                .conferenceRoom(ConferenceRoom.builder().name(bookingEntity.getConferenceRoom().getName()).roomNumber(bookingEntity.getBookingId()).capacity(bookingEntity.getNumberOfParticipants()).build())
                .slot(Slot.builder().startTime(toLocalTime(bookingEntity.getStartTime())).endTime(toLocalTime(bookingEntity.getEndTime())).build())
                .build();
    }
    public static <T> Response<T> buildSuccessResponse(T data, String message) {
        Response<T> response = buildSuccessResponse(data);
        response.setMessage(message);
        return response;
    }

    public static <T> Response<T> buildSuccessResponse(T data) {
        return Response.<T>builder()
                .data(data)
                .status(SUCCESS)
                .build();
    }
}

