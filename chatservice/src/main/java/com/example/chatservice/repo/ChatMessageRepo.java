package com.example.chatservice.repo;

import com.example.chatservice.model.ChatMessage;
import com.example.chatservice.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ChatMessageRepo extends JpaRepository<ChatMessage, UUID> {
    List<ChatMessage> findByRoomOrderBySentAtAsc(ChatRoom room);
}
