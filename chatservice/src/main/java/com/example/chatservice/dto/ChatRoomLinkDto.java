package com.example.chatservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class ChatRoomLinkDto {
    private UUID roomId;
    private String websocketUrl;
}
