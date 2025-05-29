package com.example.appointmentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDoctorDto {
    private Long doctorId;
    private Long userId;
    private String specialization;
}
