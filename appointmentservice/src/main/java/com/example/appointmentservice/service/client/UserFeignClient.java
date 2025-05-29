package com.example.appointmentservice.service.client;

import com.example.appointmentservice.config.UserClientConfig;
import com.example.appointmentservice.dto.UserDoctorDto;
import com.example.appointmentservice.dto.UserRequestDto;
import lombok.Getter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "userservice",configuration = UserClientConfig.class)
public interface UserFeignClient {

    @GetMapping("/api/v1/auth/user")
    UserRequestDto getCurrentUserIdAndEmail();

    @GetMapping("/api/v1/auth/doctors")
    List<UserDoctorDto> getAllDoctors();

}
