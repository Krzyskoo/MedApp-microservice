package com.example.userservice.service.impl;

import com.example.userservice.constants.ApplicationConstants;
import com.example.userservice.dto.UserRegistrationRequestDTO;
import com.example.userservice.dto.UserRegistrationResponseDTO;
import com.example.userservice.exception.UserAlreadyExistException;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.model.Role;
import com.example.userservice.model.User;
import com.example.userservice.repo.UserRepo;
import com.example.userservice.service.IUserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {
    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final Environment env;

    public UserServiceImpl(UserRepo userRepo, AuthenticationManager authenticationManager, Environment env) {
        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
        this.env = env;
    }

    @Override
    public UserRegistrationResponseDTO registerUser(UserRegistrationRequestDTO userRegistrationRequestDTO) {
        Optional<User> user = userRepo.findByEmail(userRegistrationRequestDTO.getEmail());
        if (user.isPresent()) {
            throw new UserAlreadyExistException("User with email " + userRegistrationRequestDTO.getEmail() + " already exists");
        }
        User savedUser = UserMapper.mapToUser(userRegistrationRequestDTO);
        userRepo.save(savedUser);
        return UserRegistrationResponseDTO.builder().message("User with email " + savedUser.getEmail() + " registered successfully").build();
    }

    @Override
    public String generateJWTToken(String email, String password) {
        String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY);
        String jwt = "";
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(email,
                password);
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);
        if(null != authenticationResponse && authenticationResponse.isAuthenticated()) {
            SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            jwt = Jwts.builder().issuer("MedApp").subject("JWT Token")
                    .claim("username", authenticationResponse.getName())
                    .claim("authorities", authenticationResponse.getAuthorities().stream().map(
                            GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                    .issuedAt(new java.util.Date())
                    .expiration(new java.util.Date((new java.util.Date()).getTime() + 30000000))
                    .signWith(secretKey).compact();

        }
        return jwt;
    }
}
