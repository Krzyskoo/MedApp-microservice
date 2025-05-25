package com.example.gatewayervice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class GatewayerviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayerviceApplication.class, args);
	}

	@Bean
	public RouteLocator medAppRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p->p
						.path("/med/auth/**")
						.filters(f->f.rewritePath("/med/auth/(?<segment>.*)","/${segment}"))
						.uri("lb://USERSERVICE"))
				.route(p->p
						.path("/payment/**")
						.filters(f->f.rewritePath("/payment/(?<segment>.*)","/${segment}"))
						.uri("lb://PAYMENTSERVICE"))
						.build();

	}



}
