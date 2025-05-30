package com.example.paymentservice.service;

import com.example.paymentservice.constants.PaymentStatus;
import com.example.paymentservice.dto.PaymentRequest;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;

public interface IPaymentService {
    String createCheckoutSession(PaymentRequest paymentRequest) throws StripeException;
    void createPaymentIntent(PaymentRequest paymentRequest) throws StripeException;
    String handleWebhook(Event stripeEvent) throws StripeException;
    String handleCheckoutSessionCompleted(Event stripeEvent) throws StripeException;
    String handlePaymentIntentSucceeded(Event stripeEvent) throws StripeException;
    String handlePaymentIntentPaymentFailed(Event stripeEvent) throws StripeException;
    void updatePaymentStatus(Long paymentIntentId, PaymentStatus status) throws StripeException;
}
