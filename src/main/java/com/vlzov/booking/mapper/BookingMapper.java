package com.vlzov.booking.mapper;

import com.vlzov.booking.dto.request.BookingRequest;
import com.vlzov.booking.dto.response.BookingResponse;
import com.vlzov.booking.entity.Booking;
import com.vlzov.booking.entity.BookingStatus;
import com.vlzov.booking.entity.Room;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public Booking toEntity(BookingRequest request, Room room) {
        return Booking.builder()
                .room(room)
                .organizer(request.getOrganizer())
                .title(request.getTitle())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .attendees(request.getAttendees())
                .status(BookingStatus.CONFIRMED)
                .build();
    }

    public BookingResponse toResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .roomId(booking.getRoom().getId())
                .roomName(booking.getRoom().getName())
                .organizer(booking.getOrganizer())
                .title(booking.getTitle())
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .attendees(booking.getAttendees())
                .status(booking.getStatus().name())
                .build();
    }

}