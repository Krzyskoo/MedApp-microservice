package com.example.chatservice.controller;

import com.example.chatservice.dto.ChatRoomLinkDto;
import com.example.chatservice.model.ChatRoom;
import com.example.chatservice.repo.ChatRoomRepo;
import com.example.chatservice.service.impl.ChatRoomServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat/rooms")
public class ChatRoomController {
    private final ChatRoomServiceImpl chatRoomService;

    public ChatRoomController(ChatRoomServiceImpl chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @GetMapping("/room/{appointmentId}")
    public ResponseEntity<ChatRoomLinkDto> getRoomLink(
            @PathVariable Long appointmentId,
            HttpServletRequest request
    ) {
        ChatRoom room = chatRoomService.getChatRoom(appointmentId);

        // Zwracamy HTTP URL do SockJS, nie ws://
        String scheme = request.getScheme();           // "http" lub "https"
        String host   = request.getServerName();       // np. "localhost"
        int    port   = request.getServerPort();       // np. 8083

        String wsUrl  = String.format("%s://%s:%d%s/ws-chat", scheme, host, port, request.getContextPath());


        return ResponseEntity.ok(new ChatRoomLinkDto(room.getId(), wsUrl));
    }
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }


}
