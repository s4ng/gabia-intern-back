package com.gmarket.api.domain.chatmessage;

import com.gmarket.api.domain.chatroom.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    public Page<ChatMessage> findByChatRoom(ChatRoom chatRoom, Pageable pageable);
}

