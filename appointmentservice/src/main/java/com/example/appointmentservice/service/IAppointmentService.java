package com.example.appointmentservice.service;

import com.example.appointmentservice.dto.AppointmentRequestDto;
import com.example.appointmentservice.dto.PaymentFinalizationEventDTO;

public interface IAppointmentService {

    String createAppointment(AppointmentRequestDto appointmentRequestDto);
    void markPaymentAsFinalized(PaymentFinalizationEventDTO paymentFinalizationEventDTO);
}
