package com.example.appointmentservice.model;

import com.example.appointmentservice.constants.AppointmentStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name="doctor_id", nullable=false)
    private Doctor doctor;
    @ManyToOne @JoinColumn(name="patient_id", nullable=false)
    private Patient patient;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private String notes;
}
