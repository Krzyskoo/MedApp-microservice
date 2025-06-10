package com.example.appointmentservice.service;

import com.example.appointmentservice.dto.AppointmentDto;
import com.example.appointmentservice.dto.AppointmentRequestDto;
import com.example.appointmentservice.dto.PaymentFinalizationEventDto;
import com.example.appointmentservice.model.Appointment;

import java.util.List;

public interface IAppointmentService {

    String createAppointment(AppointmentRequestDto appointmentRequestDto);
    void markPaymentAsFinalized(PaymentFinalizationEventDto paymentFinalizationEventDTO);
    void sendKafkaMessageAfterPaymentFinalization(Appointment appointment);
    List<AppointmentDto> getAppointments();

}
