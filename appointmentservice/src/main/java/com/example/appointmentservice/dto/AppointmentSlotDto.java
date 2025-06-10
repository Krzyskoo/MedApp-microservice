package com.example.appointmentservice.dto;

import com.example.appointmentservice.constants.AppointmentSlotStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentSlotDto {
    private Long doctorId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private AppointmentSlotStatus status;
}
