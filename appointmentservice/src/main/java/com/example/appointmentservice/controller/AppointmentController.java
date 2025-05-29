package com.example.appointmentservice.controller;

import com.example.appointmentservice.dto.AppointmentRequestDto;
import com.example.appointmentservice.service.client.UserFeignClient;
import com.example.appointmentservice.service.impl.AppointmentServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/appointment")
public class AppointmentController {

    private final AppointmentServiceImpl appointmentService;

    public AppointmentController(AppointmentServiceImpl appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAppointment(@RequestBody AppointmentRequestDto appointmentRequestDto) {
        return ResponseEntity.ok().body(appointmentService.createAppointment(appointmentRequestDto));



    }
}
