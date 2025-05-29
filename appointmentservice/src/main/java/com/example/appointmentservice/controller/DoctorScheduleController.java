package com.example.appointmentservice.controller;

import com.example.appointmentservice.service.impl.AppointmentServiceImpl;
import com.example.appointmentservice.service.impl.AppointmentSlotServiceImpl;
import com.example.appointmentservice.service.impl.DoctorScheduleServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/appointment/schedule")
public class DoctorScheduleController {

    private final DoctorScheduleServiceImpl doctorScheduleService;
    private final AppointmentSlotServiceImpl appointmentSlotService;

    public DoctorScheduleController(DoctorScheduleServiceImpl doctorScheduleService, AppointmentSlotServiceImpl appointmentSlotService) {
        this.doctorScheduleService = doctorScheduleService;
        this.appointmentSlotService = appointmentSlotService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createSchedule() {
        doctorScheduleService.createMonthlySchedule();
        appointmentSlotService.createSlotsWeakly();
        return ResponseEntity.ok().body("OK");
    }

}
