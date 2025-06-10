package com.example.appointmentservice.service;

import com.example.appointmentservice.dto.AppointmentSlotDto;
import com.example.appointmentservice.model.AppointmentSlot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentSlotService {
    List<AppointmentSlot> getAvailablesSlots(Long doctorId, LocalDate date);
    boolean checkAvailability(Long doctorId, LocalDateTime startTime);
    AppointmentSlot getSlotByStartTimeAndDoctorId(LocalDateTime startTime, Long doctorId);
    void reserveSlot(AppointmentSlot slot);
    void createSlotsWeakly();
    List<AppointmentSlotDto> getSlotsByDoctorAndDate(Long doctorId);
}
