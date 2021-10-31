package io.chat.java.api.entity.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<List<ChatRoom>> findChatRoomByStatus(ChatRoomStatus status);

    Optional<ChatRoom> findChatRoomById(Long id);
}
