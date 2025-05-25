package com.example.gatewayervice.config;

import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.server.SecurityWebFilterChain;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebFluxSecurity
public class GatewaySecurityConfig {
    private final RawTokenAuthenticationConverter tokenConverter;
    private final ReactiveJwtDecoder jwtDecoder;

    public GatewaySecurityConfig(RawTokenAuthenticationConverter tokenConverter,
                                 ReactiveJwtDecoder jwtDecoder) {
        this.tokenConverter = tokenConverter;
        this.jwtDecoder     = jwtDecoder;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .authorizeExchange(ex -> ex
                        // publiczne endpointy rejestracji/loginu
                        .pathMatchers("/med/auth/api/v1/auth/register",
                                "/med/auth/api/v1/auth/login",
                                "/med/auth/api/v1/auth/refresh-token")
                        .permitAll()
                        // WSZYSTKIE pozostałe żądania wymagają poprawnego JWT
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .bearerTokenConverter(tokenConverter)
                        .jwt(jwt -> jwt.jwtDecoder(jwtDecoder))
                )
                .build();
    }
}
