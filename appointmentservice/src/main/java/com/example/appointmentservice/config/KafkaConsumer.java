package com.example.appointmentservice.config;

import com.example.appointmentservice.dto.PaymentFinalizationEventDto;
import com.example.appointmentservice.service.impl.AppointmentServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final AppointmentServiceImpl appointmentService;

    public KafkaConsumer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper, AppointmentServiceImpl appointmentService) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.appointmentService = appointmentService;
    }

    @KafkaListener(topics = "appointment-payment-finalization", groupId = "appointment-payment-id")
    public void consumePaymentFinalizationEvent(String paymentFinalizationEventJson) {
        try {
            PaymentFinalizationEventDto paymentFinalizationEventDTO =
                    objectMapper.readValue(paymentFinalizationEventJson, PaymentFinalizationEventDto.class);
            appointmentService.markPaymentAsFinalized(paymentFinalizationEventDTO);

        } catch (Exception e) {
            // Handle the exception
        }
    }
}
