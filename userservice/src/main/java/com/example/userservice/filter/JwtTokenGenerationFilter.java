package com.example.userservice.filter;

import com.example.userservice.constants.ApplicationConstants;
import com.example.userservice.service.impl.UserServiceImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;
public class JwtTokenGenerationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null!=authentication) {
            Environment env = getEnvironment();
            if (null!=env) {
                String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                String jwt = Jwts.builder().issuer("MedApp").subject("JWT Token")
                        .claim("username", authentication.getName())
                        .claim("roles", authentication.getAuthorities().stream().map(
                                GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                        .issuedAt(new Date())
                        .expiration(new Date((new Date()).getTime() + 600000))
                        .signWith(secretKey).compact();
                response.setHeader(ApplicationConstants.JWT_HEADER, jwt);
            }
        }
        filterChain.doFilter(request, response);

    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/user");
    }

}
