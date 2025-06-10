package com.example.appointmentservice.mapper;

import com.example.appointmentservice.dto.AppointmentDto;
import com.example.appointmentservice.model.Appointment;

public class AppointmentMapper {
    public static AppointmentDto mapToAppointmentDto(Appointment appointment) {
        return new AppointmentDto(
                appointment.getId(),
                appointment.getDoctorId(),
                appointment.getStartTime(),
                appointment.getNotes(),
                appointment.getStatus());
    }
}
