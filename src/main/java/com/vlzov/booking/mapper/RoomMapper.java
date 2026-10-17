package com.vlzov.booking.mapper;

import com.vlzov.booking.dto.request.RoomRequest;
import com.vlzov.booking.dto.response.RoomResponse;
import com.vlzov.booking.entity.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

    public Room toEntity(RoomRequest request) {
        return Room.builder()
                .name(request.getName())
                .capacity(request.getCapacity())
                .location(request.getLocation())
                .build();
    }

    public RoomResponse toResponse(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .name(room.getName())
                .capacity(room.getCapacity())
                .location(room.getLocation())
                .build();
    }
}