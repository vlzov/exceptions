package com.vlzov.booking.service.impl;

import com.vlzov.booking.dto.request.BookingRequest;
import com.vlzov.booking.dto.response.BookingResponse;
import com.vlzov.booking.entity.Booking;
import com.vlzov.booking.entity.BookingStatus;
import com.vlzov.booking.entity.Room;
import com.vlzov.booking.exception.ApiException;
import com.vlzov.booking.exception.ErrorCode;
import com.vlzov.booking.mapper.BookingMapper;
import com.vlzov.booking.repository.BookingRepository;
import com.vlzov.booking.repository.RoomRepository;
import com.vlzov.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final BookingMapper bookingMapper;

    @Override
    public List<BookingResponse> getAll() {
        return bookingRepository.findAllByOrderByStartTimeAsc().stream()
                .map(bookingMapper::toResponse)
                .toList();
    }

    @Override
    public BookingResponse getById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.BOOKING_NOT_FOUND, "id", id));
        return bookingMapper.toResponse(booking);
    }

    @Override
    public List<BookingResponse> getByRoom(Long roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new ApiException(ErrorCode.ROOM_NOT_FOUND, "id", roomId);
        }
        return bookingRepository.findByRoomIdOrderByStartTimeAsc(roomId).stream()
                .map(bookingMapper::toResponse)
                .toList();
    }

    @Override
    public BookingResponse create(BookingRequest request) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ApiException(ErrorCode.ROOM_NOT_FOUND, "id", request.getRoomId()));

        if (!request.getStartTime().isBefore(request.getEndTime())) {
            throw new ApiException(ErrorCode.VALIDATION_ERROR,
                    "reason", "startTime must be before endTime");
        }

        List<Booking> overlapping = bookingRepository.findOverlapping(
                room.getId(),
                request.getStartTime(),
                request.getEndTime(),
                BookingStatus.CONFIRMED
        );
        if (!overlapping.isEmpty()) {
            throw new ApiException(ErrorCode.BOOKING_OVERLAP,
                    "conflicts", overlapping.stream().map(Booking::getId).toList());
        }

        Booking saved = this.saveBooking(request, room);

        log.info("Booking created: id={}, room={}, {} - {}",
                saved.getId(), room.getName(), saved.getStartTime(), saved.getEndTime());

        return bookingMapper.toResponse(saved);
    }

    @Transactional
    public Booking saveBooking(BookingRequest request, Room room) {
        Booking booking = bookingMapper.toEntity(request, room);
        Booking saved = bookingRepository.save(booking);

        if (saved.getAttendees() > room.getCapacity()) {
            throw new ApiException(ErrorCode.CAPACITY_EXCEEDED, "capacity", room.getCapacity());
        }

        return saved;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.BOOKING_NOT_FOUND, "id", id));
        bookingRepository.delete(booking);
    }

}