package com.example.appointmentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppointmentFinalizationEventDto {
    private Long appointmentId;
    private Long patientId;
    private Long doctorId;

}
