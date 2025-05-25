package com.example.userservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegistrationResponseDTO {
    private String message;
}
