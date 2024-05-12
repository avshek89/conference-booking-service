package com.conference.booking.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity(name = "conference_room")
public class ConferenceRoomEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @JoinColumn(name = "room_Id")
    private long roomId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int capacity;
    private Timestamp createdOn;
}