package com.example.userservice.service.impl;

import com.example.userservice.dto.UserDoctorDTO;
import com.example.userservice.mapper.DoctorProfileMapper;
import com.example.userservice.model.DoctorProfile;
import com.example.userservice.repo.DoctorProfileRepo;
import com.example.userservice.service.IDoctorProfileService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorProfileServiceImpl implements IDoctorProfileService {

    private final DoctorProfileRepo doctorProfileRepo;

    public DoctorProfileServiceImpl(DoctorProfileRepo doctorProfileRepo) {
        this.doctorProfileRepo = doctorProfileRepo;
    }

    @Override
    public List<UserDoctorDTO> getAllDoctors() {
        return doctorProfileRepo.findAll()
                .stream()
                .map(DoctorProfileMapper::mapToUserDoctorDTO)
                .collect(Collectors.toList());
    }
}
