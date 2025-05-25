package com.example.userservice.mapper;

import com.example.userservice.dto.UserRegistrationRequestDTO;
import com.example.userservice.model.User;

public class UserMapper {
    public static User mapToUser(UserRegistrationRequestDTO userRegistrationRequestDTO) {
        return User.builder()
                .username(userRegistrationRequestDTO.getUsername())
                .email(userRegistrationRequestDTO.getEmail())
                .password(userRegistrationRequestDTO.getPassword())
                .mobileNumber(userRegistrationRequestDTO.getMobileNumber())
                .build();
    }
}
