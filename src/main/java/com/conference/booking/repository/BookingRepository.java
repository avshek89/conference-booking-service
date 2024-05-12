package com.conference.booking.repository;

import com.conference.booking.entity.ConferenceBookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<ConferenceBookingEntity, Long> {
    @Query("SELECT bk from conference_booking bk where bk.startTime = :startTime AND bk.endTime = :endTime")
    List<ConferenceBookingEntity> findBookedRoomByTime(Time startTime, Time endTime);

}
