package com.example.appointmentservice.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentCreatedEvent {
    private Long appointmentId;
    private BigDecimal amount;
    private String currency;
}
