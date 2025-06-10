package com.example.chatservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ChatRoomNotFoundException extends RuntimeException{
    public ChatRoomNotFoundException(String message) {
        super(message);
    }
}
