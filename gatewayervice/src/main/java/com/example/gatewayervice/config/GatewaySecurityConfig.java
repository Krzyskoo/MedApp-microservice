package com.example.gatewayervice.config;

import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        return http
                .csrf().disable()
                .authorizeExchange(ex -> ex
                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // publiczne endpointy rejestracji/loginu
                        .pathMatchers("/med/auth/api/v1/auth/register",
                                "/med/auth/api/v1/auth/login",
                                "/med/auth/api/v1/auth/refresh-token",
                                "/payment/payment/webhook",
                                "/payment/**",
                                "/med/auth/api/v1/auth/doctors")
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
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200")); // frontend
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true);
        config.setExposedHeaders(List.of("Authorization")); // np. JWT w odpowiedzi

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}
