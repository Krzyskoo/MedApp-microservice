package com.example.appointmentservice.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentFinalizationEventDTO {
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private Long appointmentId;
    private String PatientEmail;


}
