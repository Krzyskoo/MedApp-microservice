package com.example.userservice.controller;

import com.example.userservice.constants.ApplicationConstants;
import com.example.userservice.dto.*;
import com.example.userservice.service.impl.DoctorProfileServiceImpl;
import com.example.userservice.service.impl.UserServiceImpl;
import io.jsonwebtoken.Jwt;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    private final UserServiceImpl userService;
    private final DoctorProfileServiceImpl doctorProfileService;

    public UserController(UserServiceImpl userService, DoctorProfileServiceImpl doctorProfileService) {
        this.userService = userService;
        this.doctorProfileService = doctorProfileService;
    }

    @Operation(summary = "Register a new user",
                description ="REST API to register a new user" )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description ="User with email already exists" )
    })
    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponseDTO> registerUser
            (@RequestBody UserRegistrationRequestDTO userRegistrationRequestDTO) {
        UserRegistrationResponseDTO responseDTO = userService.registerUser(userRegistrationRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    @PostMapping("/login")
    @Operation(
            summary     = "User login",
            description = "Accepts username and password and returns a JWT token in both the response header and body."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description  = "Login successful; JWT token returned in header `" + ApplicationConstants.JWT_HEADER + "`",
                    content      = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema    = @Schema(implementation = LoginResponseDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Invalid login credentials"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<LoginResponseDTO> apiLogin (@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Login data: username and password",
            required    = true,
            content     = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema    = @Schema(implementation = LoginRequestDTO.class)
            )
    )@RequestBody LoginRequestDTO loginRequest) {
        String jwt = userService.generateJWTToken(loginRequest.email(), loginRequest.password());
        return ResponseEntity.status(HttpStatus.OK).header(ApplicationConstants.JWT_HEADER,jwt)
                .body(new LoginResponseDTO(HttpStatus.OK.getReasonPhrase(), jwt));
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/user")
    public ResponseEntity<UserDTO> getCurrentUser() {
        return ResponseEntity.ok().body(userService.getAuthenticatedUser());
    }
    @GetMapping("/doctors")
    public ResponseEntity<List<UserDoctorDTO>> getAllDoctors() {
        return ResponseEntity.ok().body(doctorProfileService.getAllDoctors());
    }
}
