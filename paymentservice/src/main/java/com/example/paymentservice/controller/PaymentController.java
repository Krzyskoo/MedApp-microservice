package com.example.paymentservice.controller;

import com.example.paymentservice.dto.PaymentRequest;
import com.example.paymentservice.service.impl.PaymentServiceImpl;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentServiceImpl paymentService;
    private String stripeWebhookSecret = "whsec_rSNhtrAiLEvX8jDj9y63DJRtSN7E1xm8";

    public PaymentController(PaymentServiceImpl paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create-checkout-session")
    public ResponseEntity<String> createCheckoutSession(@RequestBody PaymentRequest paymentRequest) throws StripeException {
        return ResponseEntity.ok().body(paymentService.createCheckoutSession(paymentRequest));
    }
    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload,
                                                @RequestHeader("Stripe-Signature") String stripeRequestHeader) throws StripeException {
        Event stripeEvent = Webhook.constructEvent(payload, stripeRequestHeader, stripeWebhookSecret);
        return ResponseEntity.ok().body(paymentService.handleWebhook(stripeEvent));
    }
}
