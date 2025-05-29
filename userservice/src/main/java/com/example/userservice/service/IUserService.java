package com.example.userservice.service;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.dto.UserRegistrationRequestDTO;
import com.example.userservice.dto.UserRegistrationResponseDTO;
import com.example.userservice.model.User;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.repository.query.Param;

public interface IUserService {

    UserRegistrationResponseDTO registerUser(UserRegistrationRequestDTO userRegistrationRequestDTO);
    String generateJWTToken(String username, String password);
    UserDTO getAuthenticatedUser();
}
