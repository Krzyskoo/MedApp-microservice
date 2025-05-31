package com.example.chatservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChatRoom {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    private Long appointmentId;

    private Long doctorId;

    private Long patientId;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "room")
    @JsonManagedReference
    private List<ChatMessage> messages;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

}
