package com.vlzov.booking.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {
    private Long id;
    private Long roomId;
    private String roomName;
    private String organizer;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer attendees;
    private String status;
}