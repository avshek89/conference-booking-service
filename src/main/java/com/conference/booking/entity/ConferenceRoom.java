package com.conference.booking.entity;

import java.util.Set;
import lombok.*;
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@ToString
public class ConferenceRoom {
    private String name;
    private int capacity;
    private Set<String> maintenanceTimings;

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public Set<String> getMaintenanceTimings() {
        return maintenanceTimings;
    }
}

