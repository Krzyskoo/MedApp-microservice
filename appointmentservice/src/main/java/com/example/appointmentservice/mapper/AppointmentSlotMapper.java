package com.example.appointmentservice.mapper;

import com.example.appointmentservice.dto.AppointmentSlotDto;
import com.example.appointmentservice.model.AppointmentSlot;

public class AppointmentSlotMapper {
    public static AppointmentSlotDto mapToAppointmentSlotDto(AppointmentSlot slot) {
        return new AppointmentSlotDto(slot.getDoctorId(), slot.getStartTime(), slot.getEndTime(), slot.getStatus());
    }
}
