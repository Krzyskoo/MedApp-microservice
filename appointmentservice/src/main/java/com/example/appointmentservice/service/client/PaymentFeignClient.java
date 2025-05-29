package com.example.appointmentservice.service.client;

import com.example.appointmentservice.dto.PaymentRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "paymentservice")
public interface PaymentFeignClient {

    @PostMapping("/payment/create-checkout-session")
    String createCheckoutSession(PaymentRequestDto paymentRequestDto);
}
