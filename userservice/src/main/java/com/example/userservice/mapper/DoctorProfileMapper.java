package com.example.userservice.mapper;

import com.example.userservice.dto.UserDoctorDTO;
import com.example.userservice.model.DoctorProfile;

public class DoctorProfileMapper {
    public static UserDoctorDTO mapToUserDoctorDTO(DoctorProfile doctorProfile) {
        UserDoctorDTO userDoctorDTO = new UserDoctorDTO();
        userDoctorDTO.setUserId(doctorProfile.getUser().getUserId());
        userDoctorDTO.setDoctorId(doctorProfile.getDoctorId());
        userDoctorDTO.setSpecialization(doctorProfile.getSpecialization());
        return userDoctorDTO;

    }
}
