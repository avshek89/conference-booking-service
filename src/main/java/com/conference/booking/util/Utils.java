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

import static com.conference.booking.enums.ResponseStatus.ERROR;
import static com.conference.booking.enums.ResponseStatus.SUCCESS;
import static java.time.temporal.ChronoUnit.MINUTES;

public class Utils {

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("H[:mm]");
    private static final String SUCCESS_CODE = "00000";
    private static final String FAILURE_CODE = "00001";

    public static Time toSqlTime(LocalTime time) {
        return Time.valueOf(time);
    }

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

    public static Booking buildReservation(ConferenceBookingEntity conferenceBookingEntity) {
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
    public static <T> Response<T> buildValidResponse(String message) {
        return Response.<T>builder()
                .status(SUCCESS)
                .code(SUCCESS_CODE)
                .message(message)
                .result(true)
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
}
