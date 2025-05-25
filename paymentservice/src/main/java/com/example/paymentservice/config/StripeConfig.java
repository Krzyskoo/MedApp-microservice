package com.example.paymentservice.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class StripeConfig {
    private final Environment env;

    public StripeConfig(Environment env) {
        this.env = env;
    }
    @PostConstruct
    public void init() {
        Stripe.apiKey = env.getProperty("STRIPE_API_KEY");
    }

}
