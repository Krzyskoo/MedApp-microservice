package com.example.chatservice.service;

import com.example.chatservice.dto.AppointmentFinalizationEventDto;
import com.example.chatservice.model.ChatRoom;

public interface IChatRoomService {
    String createChatRoom(AppointmentFinalizationEventDto appointmentFinalizationEventDto);
    ChatRoom getChatRoom(Long roomId);
}
