package com.example.chatservice.repo;

import com.example.chatservice.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ChatMessageRepo extends JpaRepository<ChatMessage, UUID> {
}
