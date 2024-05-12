package com.conference.booking.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonInclude(NON_EMPTY)
public class ConferenceRoom {
    private long roomNumber;
    private String name;
    private int capacity;
}

