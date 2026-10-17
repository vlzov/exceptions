package com.vlzov.booking.repository;

import com.vlzov.booking.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
    boolean existsByName(String name);
}