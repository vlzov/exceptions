package com.vlzov.booking.config;

import com.vlzov.booking.entity.Booking;
import com.vlzov.booking.entity.BookingStatus;
import com.vlzov.booking.entity.Room;
import com.vlzov.booking.repository.BookingRepository;
import com.vlzov.booking.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (roomRepository.count() > 0) {
            return;
        }

        Room alpha = roomRepository.save(Room.builder()
                .name("Альфа").capacity(8).location("3 этаж, 301").build());
        Room beta = roomRepository.save(Room.builder()
                .name("Бета").capacity(4).location("3 этаж, 302").build());
        Room gamma = roomRepository.save(Room.builder()
                .name("Гамма").capacity(20).location("4 этаж, 401").build());

        LocalDate tomorrow = LocalDate.now().plusDays(1);

        bookingRepository.save(Booking.builder()
                .room(alpha)
                .organizer("Иван Петров")
                .title("Планирование спринта")
                .startTime(tomorrow.atTime(10, 0))
                .endTime(tomorrow.atTime(12, 0))
                .attendees(6)
                .status(BookingStatus.CONFIRMED)
                .build());

        bookingRepository.save(Booking.builder()
                .room(beta)
                .organizer("Мария Сидорова")
                .title("Собеседование")
                .startTime(tomorrow.atTime(14, 0))
                .endTime(tomorrow.atTime(15, 0))
                .attendees(2)
                .status(BookingStatus.CONFIRMED)
                .build());

        log.info("Seed data initialized: {} rooms, {} bookings",
                roomRepository.count(), bookingRepository.count());
    }

}