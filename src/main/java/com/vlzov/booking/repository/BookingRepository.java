package com.vlzov.booking.repository;

import com.vlzov.booking.entity.Booking;
import com.vlzov.booking.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("""
            SELECT b FROM Booking b
            WHERE b.room.id = :roomId
              AND b.status = :status
              AND b.startTime >= :startTime
              AND b.endTime <= :endTime
            """)
    List<Booking> findOverlapping(
            @Param("roomId") Long roomId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("status") BookingStatus status
    );

    List<Booking> findAllByOrderByStartTimeAsc();

    List<Booking> findByRoomIdOrderByStartTimeAsc(Long roomId);
}