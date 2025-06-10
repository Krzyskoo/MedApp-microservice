package com.example.paymentservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidWebhookException extends RuntimeException{
    public InvalidWebhookException(String message) {
        super(message);
    }
}
