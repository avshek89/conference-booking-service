package com.conference.booking.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.util.List;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_EMPTY)
public class AvailableConferenceRoom {
    List<ConferenceRoom> availableConferenceRooms;
}
