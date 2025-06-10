package com.example.chatservice.service.impl;

import com.example.chatservice.dto.AppointmentFinalizationEventDto;
import com.example.chatservice.exception.ChatRoomNotFoundException;
import com.example.chatservice.model.ChatRoom;
import com.example.chatservice.repo.ChatRoomRepo;
import com.example.chatservice.service.IChatRoomService;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ChatRoomServiceImpl implements IChatRoomService {
    private final ChatRoomRepo chatRoomRepo;

    public ChatRoomServiceImpl(ChatRoomRepo chatRoomRepo) {
        this.chatRoomRepo = chatRoomRepo;
    }

    @Override
    public String createChatRoom(AppointmentFinalizationEventDto appointmentFinalizationEventDto) {
        chatRoomRepo.save(
                ChatRoom.builder()
                        .appointmentId(appointmentFinalizationEventDto.getAppointmentId())
                        .patientId(appointmentFinalizationEventDto.getPatientId())
                        .doctorId(appointmentFinalizationEventDto.getDoctorId())
                        .createdAt(Instant.now())
                        .build()
        );
        return "Chat room created";
    }

    @Override
    public ChatRoom getChatRoom(Long appointmentId) {
        return chatRoomRepo.findByAppointmentId(appointmentId)
                .orElseThrow( () -> new ChatRoomNotFoundException("Chat room not found for appointment id: " + appointmentId));
    }
}
