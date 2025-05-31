package com.example.appointmentservice.config;

import com.example.appointmentservice.dto.AppointmentFinalizationEventDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }
    public void sendKafkaAppointmentFinalizationEvent(AppointmentFinalizationEventDto appointmentFinalizationEventDto) {
        try {
            String paymentFinalizationEventJson = objectMapper.writeValueAsString(appointmentFinalizationEventDto);
            kafkaTemplate.send("appointment-appointment-finalization", paymentFinalizationEventJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not send user registered event", e);
        }
    }
}
