package com.conference.booking.util;

import com.conference.booking.entity.ConferenceBookingEntity;
import com.conference.booking.entity.ConferenceRoomEntity;
import com.conference.booking.model.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static com.conference.booking.enums.ResponseStatus.ERROR;
import static com.conference.booking.enums.ResponseStatus.SUCCESS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Collections.emptyList;
import static org.springframework.util.CollectionUtils.isEmpty;

public class Utils {

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("H[:mm]");
    private static final String SUCCESS_CODE = "00000";
    private static final String FAILURE_CODE = "00001";

    public static Time toSqlTime(String time) {
        try {
            long value = dateFormat.parse(time).getTime();
            return new Time(value);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static LocalTime toLocalTime(Time time) {
        return time.toLocalTime().truncatedTo(MINUTES);
    }

    public static LocalTime toLocalTime(String time) {
        return LocalTime.parse(time, dateTimeFormatter).truncatedTo(MINUTES);
    }
    public static String currentTime() {
        return LocalTime.now().format(dateTimeFormatter);
    }

    public static Timestamp getCurrentTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static ConferenceRoom buildRoom(ConferenceRoomEntity room) {
        return ConferenceRoom.builder()
                .roomNumber(room.getRoomId())
                .name(room.getName())
                .capacity(room.getCapacity())
                .build();
    }

    public static Booking buildBooking(ConferenceBookingEntity conferenceBookingEntity) {
        return Booking.builder()
                .conferenceRoom(buildRoom(conferenceBookingEntity.getConferenceRoom()))
                .slot(buildPlan(conferenceBookingEntity.getStartTime(), conferenceBookingEntity.getEndTime()))
                .build();
    }
    public static Slot buildPlan(Time startTime, Time endTime) {
        return Slot.builder()
                .startTime(toLocalTime(startTime))
                .endTime(toLocalTime(endTime))
                .build();
    }
    public static <T> Response<T> buildErrorResponse(String message,String type) {
        return Response.<T>builder()
                .status(ERROR)
                .code(FAILURE_CODE)
                .message(message)
                .type(type)
                .result(false)
                .build();
    }
    public static ConferenceBookingEntity mapToEntity(BookingRequest bookingRequest, ConferenceRoomEntity conferenceRoomEntity) {
        return ConferenceBookingEntity.builder()
                .numberOfParticipants(bookingRequest.getNumberOfParticipants())
                .startTime(toSqlTime(bookingRequest.getStartTime()))
                .endTime(toSqlTime(bookingRequest.getEndTime()))
                .createdOn(getCurrentTime())
                .conferenceRoom(conferenceRoomEntity)
                .build();
    }
    public static AvailableConferenceRoom buildRoomAvailabilityResponse(List<ConferenceRoom> rooms) {
        return AvailableConferenceRoom.builder()
                .availableConferenceRooms(rooms)
                .build();
    }
    public static List<ConferenceRoom> buildRooms(List<ConferenceRoomEntity> rooms) {
        return rooms.stream()
                .map(Utils::buildRoom)
                .collect(Collectors.toList());
    }
    public static List<Booking> buildBookings(List<ConferenceBookingEntity> bookings) {
        if (isEmpty(bookings)) {
            return emptyList();
        }

        return bookings.stream()
                .map(Utils::buildBooking)
                .collect(Collectors.toList());
    }
    public static List<ConferenceRoom> findAvailableRooms(List<Booking> bookings, List<ConferenceRoom> rooms) {
        if (isEmpty(bookings)) {
            return rooms;
        }

        List<ConferenceRoom> bookedRooms = findBookedRooms(bookings);
        rooms.removeAll(bookedRooms);
        return rooms;
    }
    public static Booking buildBookingResponse(ConferenceBookingEntity bookingEntity) {
        return Booking.builder()
                .conferenceRoom(ConferenceRoom.builder().name(bookingEntity.getConferenceRoom().getName()).roomNumber(bookingEntity.getConferenceRoom().getRoomId()).capacity(bookingEntity.getNumberOfParticipants()).build())
                .slot(Slot.builder().startTime(toLocalTime(bookingEntity.getStartTime())).endTime(toLocalTime(bookingEntity.getEndTime())).build())
                .build();
    }
    public static <T> Response<T> buildSuccessResponse(T data, String message) {
        Response<T> response = buildSuccessResponse(data);
        response.setMessage(message);
        return response;
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
    private static List<ConferenceRoom> findBookedRooms(List<Booking> bookings) {
        return bookings.stream()
                .map(Booking::getConferenceRoom)
                .distinct()
                .collect(Collectors.toList());
    }
    public static <T> Response<T> buildSuccessResponse(T data) {
        return Response.<T>builder()
                .data(data)
                .status(SUCCESS)
                .code(SUCCESS_CODE)
                .result(true)
                .build();
    }
}
