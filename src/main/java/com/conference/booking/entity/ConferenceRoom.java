package com.conference.booking.entity;

import java.util.Set;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class ConferenceRoom {
    private String name;
    private int capacity;
    private Set<String> maintenanceTimings;
}

