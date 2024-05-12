package com.conference.booking.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Data
@ToString
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Slot implements Comparable<Slot>{

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "HH:[mm]")
    private LocalTime startTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "HH:[mm]")
    private LocalTime endTime;

    @Override
    public int compareTo(Slot schedule) {
        if (this.startTime.isAfter(schedule.startTime)) {
            return 1;
        } else if (this.startTime.isBefore(schedule.startTime)) {
            return -1;
        } else {
            return 0;
        }
    }
}
