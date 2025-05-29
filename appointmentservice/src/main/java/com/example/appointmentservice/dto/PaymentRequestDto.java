package com.example.appointmentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentRequestDto {
    private BigDecimal amount;
    private String currency;
    private Long appointmentId;
    private String customerEmail;
}
