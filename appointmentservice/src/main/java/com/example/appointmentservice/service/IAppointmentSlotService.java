package com.example.appointmentservice.service;

import com.example.appointmentservice.model.AppointmentSlot;

import java.time.LocalDate;
import java.util.List;

public interface IAppointmentSlotService {
    List<AppointmentSlot> getAvailablesSlots(Long doctorId, LocalDate date);
    void createSlotsWeakly();
}
