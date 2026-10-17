package com.vlzov.booking.service;

import com.vlzov.booking.dto.request.RoomRequest;
import com.vlzov.booking.dto.response.RoomResponse;

import java.util.List;

public interface RoomService {

    List<RoomResponse> getAll();

    RoomResponse getById(Long id);

    RoomResponse create(RoomRequest request);

}