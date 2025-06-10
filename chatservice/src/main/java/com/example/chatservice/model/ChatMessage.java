package com.example.chatservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatMessage {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    @JsonBackReference
    private ChatRoom room;

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Column(name = "content", nullable = false)
    private String content;

    @JsonIgnore
    @Column(name = "sent_at", nullable = false)
    private Instant sentAt;
    @Column(name = "file_name")
    private String fileName;

    @Lob
    @Column(name = "file_data", columnDefinition = "TEXT")
    private String fileData;
}
