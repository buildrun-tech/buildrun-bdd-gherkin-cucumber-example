package tech.buildrun.cucumber.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record BookingRequest(
    Long roomId,
    @JsonFormat(shape = JsonFormat.Shape.STRING) LocalDateTime startTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING) LocalDateTime endTime
) {}