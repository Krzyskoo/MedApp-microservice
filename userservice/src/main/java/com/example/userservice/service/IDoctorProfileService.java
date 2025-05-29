package com.example.userservice.service;

import com.example.userservice.dto.UserDoctorDTO;
import com.example.userservice.model.DoctorProfile;

import java.util.List;

public interface IDoctorProfileService {
    List<UserDoctorDTO> getAllDoctors();
}
