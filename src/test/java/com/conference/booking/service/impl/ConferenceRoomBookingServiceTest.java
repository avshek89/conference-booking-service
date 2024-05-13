package com.conference.booking.service.impl;

import com.conference.booking.entity.ConferenceBookingEntity;
import com.conference.booking.entity.ConferenceRoomEntity;
import com.conference.booking.model.AvailableConferenceRoom;
import com.conference.booking.model.Booking;
import com.conference.booking.model.BookingRequest;
import com.conference.booking.model.Response;
import com.conference.booking.repository.BookingRepository;
import com.conference.booking.repository.ConferenceRoomRepository;
import com.conference.booking.services.impl.ConferenceRoomBookingServiceImpl;
import com.conference.booking.validator.BookingTimeValidator;
import com.conference.booking.validator.MaintenanceSlotValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Arrays;
import java.util.List;
import static com.conference.booking.util.Utils.toSqlTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConferenceRoomBookingServiceTest {

    @InjectMocks
    ConferenceRoomBookingServiceImpl roomBookingService;
    @Mock
    private BookingRepository rookingRepository;
    @Mock
    private ConferenceRoomRepository conferenceRoomRepository;
    @Mock
    private MaintenanceSlotValidator maintenanceSlotValidator;
    @Mock
    private BookingTimeValidator bookingTimeValidator;


    @Test
    public void bookConferenceRoomTest() {
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setNumberOfParticipants(3);
        bookingRequest.setStartTime("11:00");
        bookingRequest.setEndTime("11:30");

        List<ConferenceRoomEntity> listOfConferenceRoom = Arrays.asList(ConferenceRoomEntity.builder().build().builder().roomId(001l).name("AMAZE").capacity(3).build(),
                ConferenceRoomEntity.builder().roomId(002l).name("BEAUTY").capacity(7).build(),
                ConferenceRoomEntity.builder().roomId(003l).name("INSPIRE").capacity(12).build(),
                ConferenceRoomEntity.builder().roomId(004l).name("STRIVE").capacity(21).build());

        List<ConferenceBookingEntity> allBookingEntity = Arrays.asList(ConferenceBookingEntity.builder().bookingId(001l).startTime(toSqlTime("10:00")).
                        endTime(toSqlTime("10:30")).numberOfParticipants(3).conferenceRoom(ConferenceRoomEntity.builder().roomId(001l).name("BEAUTY").capacity(3).build()).build());
        when(rookingRepository.findBookedRoomByTime(any(), any())).thenReturn(allBookingEntity);
        when(conferenceRoomRepository.findAllByOrderByCapacityAsc()).thenReturn(listOfConferenceRoom);
        Response<Booking> booking = roomBookingService.bookConferenceRoom(bookingRequest);
        assertNotNull(booking);
        assertEquals("00000", booking.getCode());
    }

    @Test
    public void getAvailableConferenceRoomsTest() {
        List<ConferenceRoomEntity> listOfConferenceRoom = Arrays.asList(ConferenceRoomEntity.builder().build().builder().roomId(001l).name("AMAZE").capacity(3).build(),
                ConferenceRoomEntity.builder().roomId(002l).name("BEAUTY").capacity(7).build(),
                ConferenceRoomEntity.builder().roomId(003l).name("INSPIRE").capacity(12).build(),
                ConferenceRoomEntity.builder().roomId(004l).name("STRIVE").capacity(21).build());

        List<ConferenceBookingEntity> allBookingEntity = Arrays.asList(ConferenceBookingEntity.builder().bookingId(001l).startTime(toSqlTime("10:00")).
                endTime(toSqlTime("10:30")).numberOfParticipants(3).conferenceRoom(ConferenceRoomEntity.builder().roomId(001l).name("BEAUTY").capacity(3).build()).build());
        when(rookingRepository.findBookedRoomByTime(any(), any())).thenReturn(allBookingEntity);
        when(conferenceRoomRepository.findAllByOrderByCapacityAsc()).thenReturn(listOfConferenceRoom);
        Response<AvailableConferenceRoom> response = roomBookingService.getAvailableConferenceRooms("10:30","11:00");

        assertNotNull(response);
        assertEquals("00000", response.getCode());
    }
}
