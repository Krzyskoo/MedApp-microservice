package com.example.chatservice.kafka;

import com.example.chatservice.dto.AppointmentFinalizationEventDto;
import com.example.chatservice.service.impl.ChatRoomServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerConfig {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final ChatRoomServiceImpl chatRoomService;

    public KafkaConsumerConfig(KafkaTemplate<String, String> kafkaTemplate,
                               ObjectMapper objectMapper,
                               ChatRoomServiceImpl chatRoomService) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.chatRoomService = chatRoomService;
    }

    @KafkaListener(topics = "appointment-appointment-finalization", groupId = "appointment-finalization-id")
    public void consumePaymentFinalizationEvent(String paymentFinalizationEventJson) {
        try {
            AppointmentFinalizationEventDto appointmentFinalizationEventDto =
                    objectMapper.readValue(paymentFinalizationEventJson, AppointmentFinalizationEventDto.class);
            chatRoomService.createChatRoom(appointmentFinalizationEventDto);

        } catch (Exception e) {
            // Handle the exception
        }
    }
}
