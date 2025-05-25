package com.example.gatewayervice.config;

import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Configuration
public class JwtDecoderConfig {
    private final Environment environment;

    public JwtDecoderConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        byte[] bytes = environment.getProperty("JWT_SECRET").getBytes(StandardCharsets.UTF_8);
        SecretKey key = Keys.hmacShaKeyFor(bytes);

        NimbusReactiveJwtDecoder decoder =
                NimbusReactiveJwtDecoder
                        .withSecretKey(key)
                        .macAlgorithm(MacAlgorithm.HS512)
                        .build();

        OAuth2TokenValidator<Jwt> validator = new DelegatingOAuth2TokenValidator<>(
                JwtValidators.createDefaultWithIssuer("MedApp"),
                new JwtTimestampValidator()
        );
        decoder.setJwtValidator(validator);

        return decoder;
    }
}
