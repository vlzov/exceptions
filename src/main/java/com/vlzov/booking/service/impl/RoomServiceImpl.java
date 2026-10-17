package com.vlzov.booking.service.impl;

import com.vlzov.booking.dto.request.RoomRequest;
import com.vlzov.booking.dto.response.RoomResponse;
import com.vlzov.booking.entity.Room;
import com.vlzov.booking.exception.ApiException;
import com.vlzov.booking.exception.ErrorCode;
import com.vlzov.booking.mapper.RoomMapper;
import com.vlzov.booking.repository.RoomRepository;
import com.vlzov.booking.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Override
    public List<RoomResponse> getAll() {
        return roomRepository.findAll().stream()
                .map(roomMapper::toResponse)
                .toList();
    }

    @Override
    public RoomResponse getById(Long id) {
        return roomRepository.findById(id)
                .map(roomMapper::toResponse)
                .orElseThrow(() -> new ApiException(ErrorCode.ROOM_NOT_FOUND, "id", id));
    }

    @Override
    @Transactional
    public RoomResponse create(RoomRequest request) {
        if (roomRepository.existsByName(request.getName())) {
            throw new ApiException(ErrorCode.ROOM_NAME_TAKEN, "name", request.getName());
        }
        Room room = roomMapper.toEntity(request);
        return roomMapper.toResponse(roomRepository.save(room));
    }

}