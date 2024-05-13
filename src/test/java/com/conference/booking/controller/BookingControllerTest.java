package com.conference.booking.controller;

import com.conference.booking.model.*;
import com.conference.booking.services.ConferenceRoomBookingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static com.conference.booking.enums.ResponseStatus.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class BookingControllerTest {

    @InjectMocks
    private BookingController bookingController;
    @Mock
    private ConferenceRoomBookingService bookingService;
    @Test
    public void bookConferenceRoomTest() {

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setNumberOfParticipants(3);
        bookingRequest.setStartTime("11:00");
        bookingRequest.setEndTime("11.30");
        Booking booking = Booking.builder().conferenceRoom(ConferenceRoom.builder().roomNumber(001l).name("AMAZE").capacity(3).build()).build();
        Response<Booking> mockResp = Response.<Booking>builder().status(SUCCESS).code("00000").message("success").data(booking).result(true).build();

        when(bookingService.bookConferenceRoom(any())).thenReturn(mockResp);
        ResponseEntity<Response<Booking>> response = bookingController.bookConferenceRoom(bookingRequest);

        assertNotNull(response);
        assertEquals("00000",response.getBody().getCode());
        assertEquals("AMAZE",response.getBody().getData().getConferenceRoom().getName());
    }

    @Test
    public void getAvailableConferenceRoomsTest() {

        List<ConferenceRoom> availableConferenceRooms = Arrays.asList(ConferenceRoom.builder().roomNumber(001l).name("AMAZE").capacity(3).build(),
                ConferenceRoom.builder().roomNumber(002l).name("BEAUTY").capacity(7).build(),
                ConferenceRoom.builder().roomNumber(003l).name("INSPIRE").capacity(12).build(),
                ConferenceRoom.builder().roomNumber(002l).name("STRIVE").capacity(21).build());
        AvailableConferenceRoom availableConferenceRoom = AvailableConferenceRoom.builder().availableConferenceRooms(availableConferenceRooms).build();
        Response<AvailableConferenceRoom> mockResp = Response.<AvailableConferenceRoom>builder().status(SUCCESS).code("00000").message("success").data(availableConferenceRoom).result(true).build();

        when(bookingService.getAvailableConferenceRooms(anyString(),anyString())).thenReturn(mockResp);
        ResponseEntity<Response<AvailableConferenceRoom>> response = bookingController.getAvailableConferenceRooms("11:00","11:30");

        assertNotNull(response);
        assertEquals("00000",response.getBody().getCode());
        assertEquals(4,response.getBody().getData().getAvailableConferenceRooms().size());
    }

}
