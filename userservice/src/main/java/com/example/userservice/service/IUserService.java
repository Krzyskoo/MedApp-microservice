package com.example.userservice.service;

import com.example.userservice.dto.UserRegistrationRequestDTO;
import com.example.userservice.dto.UserRegistrationResponseDTO;
import org.springframework.data.repository.query.Param;

public interface IUserService {

    UserRegistrationResponseDTO registerUser(UserRegistrationRequestDTO userRegistrationRequestDTO);
    String generateJWTToken(String username, String password);
}
