package com.example.paymentservice.model;

import com.example.paymentservice.constants.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment",indexes = @Index(name = "idx_payment_appointment", columnList = "appointment_id"))
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "appointment_id", nullable = false)
    private Long appointmentId;

    @Column(name = "intent_id", nullable = true, unique = true)
    private String intentId;
    private String customerEmail;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "currency", length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30)
    private PaymentStatus status;


}
