package com.example.paymentservice.service.impl;

import com.example.paymentservice.config.KafkaProducer;
import com.example.paymentservice.constants.PaymentStatus;
import com.example.paymentservice.dto.PaymentFinalizationEventDTO;
import com.example.paymentservice.dto.PaymentRequest;
import com.example.paymentservice.exception.AppointmentNotFoundException;
import com.example.paymentservice.exception.InvalidWebhookException;
import com.example.paymentservice.exception.PaymentNotFoundException;
import com.example.paymentservice.model.Payment;
import com.example.paymentservice.repo.PaymentRepo;
import com.example.paymentservice.service.IPaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@Service
public class PaymentServiceImpl implements IPaymentService {
    private final PaymentRepo paymentRepo;
    private final KafkaProducer kafkaProducer;

    public PaymentServiceImpl(PaymentRepo paymentRepo, KafkaProducer kafkaProducer) {
        this.paymentRepo = paymentRepo;
        this.kafkaProducer = kafkaProducer;
    }

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
                .setPaymentIntentData(SessionCreateParams.PaymentIntentData.builder()
                        .putMetadata("appointmentId", String.valueOf(paymentRequest.getAppointmentId()))
                        .putMetadata("patientEmail", String.valueOf(paymentRequest.getCustomerEmail()))
                        .build())
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
        createPaymentIntent(paymentRequest);
        return session.getUrl();
    }

    @Override
    public void createPaymentIntent(PaymentRequest paymentRequest){
        Payment payment = Payment.builder()
                .appointmentId(paymentRequest.getAppointmentId())
                .intentId(paymentRequest.getAppointmentId().toString())
                .amount(paymentRequest.getAmount())
                .currency(paymentRequest.getCurrency())
                .status(PaymentStatus.CREATED)
                .customerEmail(paymentRequest.getCustomerEmail())
                .build();
        paymentRepo.save(payment);
    }

    @Override
    public String handleWebhook(Event stripeEvent){
        String eventType = stripeEvent.getType();
        return switch (eventType) {
            case "checkout.session.completed" -> handleCheckoutSessionCompleted(stripeEvent);
            case "payment_intent.succeeded" -> handlePaymentIntentSucceeded(stripeEvent);
            case "payment_intent.payment_failed" -> handlePaymentIntentPaymentFailed(stripeEvent);
            default -> throw new InvalidWebhookException("Invalid event type: " + eventType);
        };
    }

    @Override
    public String handleCheckoutSessionCompleted(Event stripeEvent){
        Session session = (Session) stripeEvent.getData().getObject();
        if (session == null) {
            return "Bad request";
        }
        Payment payment = paymentRepo.findByAppointmentId(Long.parseLong(session.getMetadata().get("appointmentId")));
        if (payment == null) {
            throw new AppointmentNotFoundException("Appointment not found");
        }
        payment.setIntentId(session.getPaymentIntent());
        paymentRepo.save(payment);
        return "Success";

    }

    @Override
    public String handlePaymentIntentSucceeded(Event stripeEvent){

        Long appointmentId = Long.parseLong(((PaymentIntent) stripeEvent.getData()
                .getObject())
                .getMetadata()
                .get("appointmentId"));

        updatePaymentStatus(appointmentId, PaymentStatus.SUCCEEDED);
        return "Success";
    }

    @Override
    public String handlePaymentIntentPaymentFailed(Event stripeEvent){
        Long appointmentId = Long.parseLong(((PaymentIntent) stripeEvent.getData()
                .getObject())
                .getMetadata()
                .get("appointmentId"));

        updatePaymentStatus(appointmentId, PaymentStatus.FAILED);
        return "Success from falied";
    }

    @Override
    public void updatePaymentStatus(Long appointmentId, PaymentStatus status){
        Payment payment = paymentRepo.findByAppointmentId(appointmentId);
        if (payment==null){
            throw new PaymentNotFoundException("Payment not found");
        }
        payment.setStatus(status);
        paymentRepo.save(payment);
        kafkaProducer.sendKafkaPaymentFinalizationEvent(new PaymentFinalizationEventDTO(
                status,
                payment.getAppointmentId(),
                payment.getCustomerEmail()
                ));
    }
}
