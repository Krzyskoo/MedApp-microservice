package com.example.chatservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {
    private UUID id;
    private Long   senderId;
    private String content;
    private Instant sentAt;
    private String fileName;
    private String fileData;
}
