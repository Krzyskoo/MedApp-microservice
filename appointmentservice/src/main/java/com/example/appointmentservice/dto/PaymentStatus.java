package com.example.appointmentservice.dto;

public enum PaymentStatus {
    FAILED,
    SUCCEEDED,
    CANCELLED,
    PROCESSING,
    CREATED,
    REQUIRES_ACTION,
    REQUIRES_CONFIRMATION,
    REQUIRES_PAYMENT_METHOD
}
