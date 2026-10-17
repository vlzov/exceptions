package com.vlzov.booking.service;

import com.vlzov.booking.dto.request.BookingRequest;
import com.vlzov.booking.dto.response.BookingResponse;

import java.util.List;

public interface BookingService {

    List<BookingResponse> getAll();

    BookingResponse getById(Long id);

    List<BookingResponse> getByRoom(Long roomId);

    BookingResponse create(BookingRequest request);

    void delete(Long id);

}