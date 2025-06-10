package com.example.appointmentservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AppointmentSlotNotFoundException extends RuntimeException {
    public AppointmentSlotNotFoundException(String message) {
        super(message);
    }
}
