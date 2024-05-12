package com.conference.booking.repository;

import com.conference.booking.entity.ConferenceRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ConferenceRoomRepository extends JpaRepository<ConferenceRoomEntity, Long> {
    List<ConferenceRoomEntity> findAllByOrderByCapacityAsc();
}
