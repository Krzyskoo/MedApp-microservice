package com.example.appointmentservice.controller;

import com.example.appointmentservice.dto.AppointmentDto;
import com.example.appointmentservice.dto.AppointmentRequestDto;
import com.example.appointmentservice.dto.AppointmentSlotDto;
import com.example.appointmentservice.service.client.UserFeignClient;
import com.example.appointmentservice.service.impl.AppointmentServiceImpl;
import com.example.appointmentservice.service.impl.AppointmentSlotServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/appointment")
public class AppointmentController {

    private final AppointmentServiceImpl appointmentService;
    private final AppointmentSlotServiceImpl appointmentSlotService;

    public AppointmentController(AppointmentServiceImpl appointmentService, AppointmentSlotServiceImpl appointmentSlotService) {
        this.appointmentService = appointmentService;
        this.appointmentSlotService = appointmentSlotService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAppointment(@RequestBody AppointmentRequestDto appointmentRequestDto) {
        return ResponseEntity.ok()
                .body(appointmentService.createAppointment(appointmentRequestDto));

    }
    @GetMapping("/slots/{doctorId}")
    public ResponseEntity<List<AppointmentSlotDto>> getSlots(@PathVariable Long doctorId) {
        return ResponseEntity.ok()
                .body(appointmentSlotService.getSlotsByDoctorAndDate(doctorId));
    }
    @GetMapping("/get")
    public ResponseEntity<List<AppointmentDto>> getAppointments() {
        return ResponseEntity.ok()
                .body(appointmentService.getAppointments());

    }
}
