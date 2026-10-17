package com.vlzov.booking.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    ROOM_NOT_FOUND     (HttpStatus.NOT_FOUND,    "Переговорная комната не найдена"),
    ROOM_NAME_TAKEN    (HttpStatus.CONFLICT,     "Комната с таким названием уже существует"),
    BOOKING_NOT_FOUND  (HttpStatus.NOT_FOUND,    "Бронирование не найдено"),
    BOOKING_OVERLAP    (HttpStatus.CONFLICT,     "Комната уже забронирована на это время"),
    CAPACITY_EXCEEDED  (HttpStatus.BAD_REQUEST,  "Количество участников превышает вместимость комнаты"),
    VALIDATION_ERROR   (HttpStatus.BAD_REQUEST,  "Ошибка валидации"),
    INTERNAL_ERROR     (HttpStatus.INTERNAL_SERVER_ERROR, "Внутренняя ошибка");

    public final HttpStatus status;
    public final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}