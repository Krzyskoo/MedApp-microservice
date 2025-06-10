package com.example.paymentservice.config;

import com.example.paymentservice.dto.PaymentFinalizationEventDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }
    public void sendKafkaPaymentFinalizationEvent(PaymentFinalizationEventDTO paymentFinalizationEventDTO) {
        try {
            String paymentFinalizationEventJson = objectMapper.writeValueAsString(paymentFinalizationEventDTO);
            kafkaTemplate.send("appointment-payment-finalization", paymentFinalizationEventJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not send user payment finalization event", e);
        }
    }
}
