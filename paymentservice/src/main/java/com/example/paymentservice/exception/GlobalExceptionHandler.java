package com.example.paymentservice.exception;

import com.example.paymentservice.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleAppointmentNotFoundException(AppointmentNotFoundException exception, WebRequest webRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handlePaymentNotFoundException(PaymentNotFoundException exception, WebRequest webRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InvalidWebhookException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidWebhookException(InvalidWebhookException exception, WebRequest webRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }
}
