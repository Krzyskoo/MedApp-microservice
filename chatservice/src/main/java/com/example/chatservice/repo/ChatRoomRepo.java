package com.example.chatservice.repo;

import com.example.chatservice.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface ChatRoomRepo extends JpaRepository<ChatRoom, UUID> {
    Optional<ChatRoom> findByAppointmentId(Long appointmentId);
}
