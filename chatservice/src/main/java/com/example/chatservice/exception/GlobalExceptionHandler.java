package com.example.chatservice.exception;

import com.example.chatservice.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ChatRoomNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleChatRoomNotFoundException(ChatRoomNotFoundException exception, WebRequest webRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now());
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

}
