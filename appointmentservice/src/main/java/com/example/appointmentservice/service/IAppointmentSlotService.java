package com.example.appointmentservice.service;

import com.example.appointmentservice.model.AppointmentSlot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentSlotService {
    List<AppointmentSlot> getAvailablesSlots(Long doctorId, LocalDate date);
    boolean checkAvailability(Long doctorId, LocalDateTime startTime);
    void reserveSlot(Long doctorId, LocalDateTime startTime);
    void createSlotsWeakly();
}
