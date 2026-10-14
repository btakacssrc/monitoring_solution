package com.aldisued.iot.monitoring.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record AlertDto(
        @NotNull
        UUID sensorId,
        @NotEmpty
        String message,
        @NotNull
        LocalDateTime timestamp
) {
}
