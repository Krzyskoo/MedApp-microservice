package com.example.chatservice.controller;

import com.example.chatservice.model.ChatMessage;
import com.example.chatservice.repo.ChatMessageRepo;
import com.example.chatservice.repo.ChatRoomRepo;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@Controller
public class ChatController {
    private final ChatRoomRepo roomRepo;
    private final ChatMessageRepo msgRepo;

    public ChatController(ChatRoomRepo roomRepo, ChatMessageRepo msgRepo) {
        this.roomRepo = roomRepo;
        this.msgRepo = msgRepo;
    }

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/chat/{roomId}")
    public ChatMessage sendMessage(@Payload ChatMessage incoming) {
        System.out.println(">>> [ChatController] sendMessage() wywołano dla roomId=" +
                ", content=\"" + incoming.getContent() + "\"");
        ChatMessage msg = new ChatMessage();
        msg.setRoom(
                roomRepo.findById(incoming.getRoom().getId()).orElseThrow()
        );
        msg.setSenderId(incoming.getSenderId());
        msg.setContent(incoming.getContent());
        msg.setSentAt(Instant.now());
        if (incoming.getFileData() != null) {
            msg.setFileName(incoming.getFileName());
            msg.setFileData(incoming.getFileData());
        }
        return msgRepo.save(msg);
    }
}
