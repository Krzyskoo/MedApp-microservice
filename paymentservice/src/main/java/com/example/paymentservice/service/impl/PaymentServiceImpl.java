package com.example.paymentservice.service.impl;

import com.example.paymentservice.dto.PaymentRequest;
import com.example.paymentservice.service.IPaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PaymentServiceImpl implements IPaymentService {

    @Override
    public String createCheckoutSession(PaymentRequest paymentRequest) throws StripeException {
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.BLIK)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/success")
                .setCancelUrl("http://localhost:3000/cancel")
                .putMetadata("appointmentId", String.valueOf(paymentRequest.getAppointmentId()))
                .putMetadata("patientEmail", String.valueOf(paymentRequest.getCustomerEmail()))
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency(paymentRequest.getCurrency())
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Appointment")
                                                                .build()
                                                )
                                                .setUnitAmount(paymentRequest.getAmount())
                                                .build()
                                )
                                .setQuantity(1L)
                                .build()
                )
                .build();
        Session session = Session.create(params);
        return session.getUrl();
    }

    @Override
    public String handleWebhook(Event stripeEvent) throws StripeException {
        return null;
    }

    @Override
    public String handleCheckoutSessionCompleted(Event stripeEvent) throws StripeException {
        return null;
    }

    @Override
    public String handlePaymentIntentSucceeded(Event stripeEvent) throws StripeException {
        return null;
    }
}
