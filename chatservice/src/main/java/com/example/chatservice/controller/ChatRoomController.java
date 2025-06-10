package com.example.chatservice.controller;

import com.example.chatservice.dto.ChatMessageDto;
import com.example.chatservice.dto.ChatRoomLinkDto;
import com.example.chatservice.model.ChatMessage;
import com.example.chatservice.model.ChatRoom;
import com.example.chatservice.repo.ChatMessageRepo;
import com.example.chatservice.repo.ChatRoomRepo;
import com.example.chatservice.service.impl.ChatRoomServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/chat/rooms")
public class ChatRoomController {
    private final ChatRoomServiceImpl chatRoomService;
    private final ChatRoomRepo chatRoomRepo;
    private final ChatMessageRepo chatMessageRepo;

    public ChatRoomController(ChatRoomServiceImpl chatRoomService, ChatRoomRepo chatRoomRepo, ChatMessageRepo chatMessageRepo) {
        this.chatRoomService = chatRoomService;
        this.chatRoomRepo = chatRoomRepo;
        this.chatMessageRepo = chatMessageRepo;
    }

    @GetMapping("/room/{appointmentId}")
    public ResponseEntity<ChatRoomLinkDto> getRoomLink(
            @PathVariable Long appointmentId,
            HttpServletRequest request
    ) {
        ChatRoom room = chatRoomService.getChatRoom(appointmentId);

        String scheme = request.getScheme();
        String host   = request.getServerName();
        int    port   = request.getServerPort();

        String wsUrl  = String.format("%s://%s:%d%s/ws-chat", scheme, host, port, request.getContextPath());


        return ResponseEntity.ok(new ChatRoomLinkDto(room.getId(), wsUrl));
    }

    @GetMapping("/{roomId}/history")
    public ResponseEntity<List<ChatMessageDto>> getChatHistory(@PathVariable("roomId") String roomId) {

        ChatRoom room = chatRoomRepo.findById(UUID.fromString(roomId))
                .orElseThrow(() -> new IllegalArgumentException("Room not found: " + roomId));

        List<ChatMessage> messages = chatMessageRepo.findByRoomOrderBySentAtAsc(room);

        List<ChatMessageDto> history = messages.stream()
                .map(m -> new ChatMessageDto(
                        m.getId(),
                        m.getSenderId(),
                        m.getContent(),
                        m.getSentAt(),
                        m.getFileName(),
                        m.getFileData()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(history);
    }


    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }


}
