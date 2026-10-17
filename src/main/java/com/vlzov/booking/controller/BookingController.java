package com.vlzov.booking.controller;

import com.vlzov.booking.dto.request.BookingRequest;
import com.vlzov.booking.dto.request.RoomRequest;
import com.vlzov.booking.dto.response.BookingResponse;
import com.vlzov.booking.dto.response.RoomResponse;
import com.vlzov.booking.service.BookingService;
import com.vlzov.booking.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <h2>REST API контроллер</h2>
 * <p>Базовый путь: <code>/api</code></p>
 *
 * <h3>Комнаты (Rooms)</h3>
 * <ul>
 *   <li>GET <code>/api/rooms</code> — список комнат</li>
 *   <li>GET <code>/api/rooms/{id}</code> — комната по id</li>
 *   <li>POST <code>/api/rooms</code> — создать комнату</li>
 * </ul>
 *
 * <h3>Бронирования (Bookings)</h3>
 * <ul>
 *   <li>GET <code>/api/bookings</code> — список бронирований</li>
 *   <li>GET <code>/api/bookings/{id}</code> — бронирование по id</li>
 *   <li>GET <code>/api/rooms/{roomId}/bookings</code> — бронирования комнаты</li>
 *   <li>POST <code>/api/bookings</code> — создать бронирование</li>
 *   <li>DELETE <code>/api/bookings/{id}</code> — удалить бронирование</li>
 * </ul>
 *
 * @since 26-09-2026
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final RoomService roomService;

    @GetMapping("/rooms")
    public List<RoomResponse> getAllRooms() {
        return roomService.getAll();
    }

    @GetMapping("/rooms/{id}")
    public RoomResponse getRoom(@PathVariable Long id) {
        return roomService.getById(id);
    }

    @PostMapping("/rooms")
    public ResponseEntity<RoomResponse> createRoom(@Valid @RequestBody RoomRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.create(request));
    }

    @GetMapping("/bookings")
    public List<BookingResponse> getAllBookings() {
        return bookingService.getAll();
    }

    @GetMapping("/bookings/{id}")
    public BookingResponse getBooking(@PathVariable Long id) {
        return bookingService.getById(id);
    }

    @GetMapping("/rooms/{roomId}/bookings")
    public List<BookingResponse> getBookingsByRoom(@PathVariable Long roomId) {
        return bookingService.getByRoom(roomId);
    }

    @PostMapping("/bookings")
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.create(request));
    }

    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}