package com.conference.booking.entity;

import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConferenceRoom that = (ConferenceRoom) o;
        return capacity == that.capacity && Objects.equals(name, that.name) && Objects.equals(maintenanceTimings, that.maintenanceTimings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, capacity, maintenanceTimings);
    }
}

