package com.vlzov.booking.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequest {

    @NotNull(message = "ID комнаты обязателен")
    private Long roomId;

    @NotBlank(message = "Имя организатора обязательно")
    @Size(max = 100)
    private String organizer;

    @NotBlank(message = "Название встречи обязательно")
    @Size(max = 150)
    private String title;

    @NotNull(message = "Время начала обязательно")
    @Future(message = "Время начала должно быть в будущем")
    private LocalDateTime startTime;

    @NotNull(message = "Время окончания обязательно")
    @Future(message = "Время окончания должно быть в будущем")
    private LocalDateTime endTime;

    @NotNull(message = "Количество участников обязательно")
    @Positive(message = "Количество участников должно быть положительным")
    private Integer attendees;

}