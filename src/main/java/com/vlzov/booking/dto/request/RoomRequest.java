package com.vlzov.booking.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomRequest {

    @NotBlank(message = "Название комнаты обязательно")
    @Size(max = 100)
    private String name;

    @NotNull(message = "Вместимость обязательна")
    @Positive(message = "Вместимость должна быть положительной")
    private Integer capacity;

    @NotBlank(message = "Расположение обязательно")
    @Size(max = 150)
    private String location;

}