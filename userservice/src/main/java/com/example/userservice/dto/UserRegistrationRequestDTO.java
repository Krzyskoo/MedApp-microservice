package com.example.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "UserRegistrationRequestDTO", description = "User registration details")
public class UserRegistrationRequestDTO {

    @Schema(description = "Name of the user", example = "John Doe")
    private String username;
    @Schema(description = "Email address of the customer", example = "tutor@eazybytes.com")
    private String email;
    @Schema(description = "Password of the user", example = "password")
    private String password;
    @Schema(description = "Mobile Number of the customer", example = "9345432123")
    private String mobileNumber;

}
