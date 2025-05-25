package com.example.paymentservice.model;

import com.example.paymentservice.constants.PaymentStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "payment",indexes = @Index(name = "idx_payment_appointment", columnList = "appointment_id"))
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "appointment_id", nullable = false)
    private Long appointmentId;

    @Column(name = "intent_id", nullable = false, unique = true)
    private String intentId;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "currency", length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30)
    private PaymentStatus status;

    @Column(name = "last_error_message")
    private String lastErrorMessage;
}
